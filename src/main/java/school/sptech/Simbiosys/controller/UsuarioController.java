package school.sptech.Simbiosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.model.Usuario;
import school.sptech.Simbiosys.repository.UsuarioRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("O nome é obrigatório.");
        }

        if (usuario.getEmail() == null || !usuario.getEmail().contains("@") || !usuario.getEmail().contains(".")) {
            return ResponseEntity.badRequest().body("Email inválido.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            return ResponseEntity.badRequest().body("A senha deve ter pelo menos 6 caracteres.");
        }

        if (repository.existsByEmailIgnoreCaseContaining(usuario.getEmail())) {
            return ResponseEntity.status(409).body("Já existe um usuário cadastrado com este email.");
        }

        usuario.setId(null);
        Usuario usuarioSalvo = repository.save(usuario);

        return ResponseEntity.status(201).body(usuarioSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {

        var usuarios = repository.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> usuario = repository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable Integer id,
            @RequestBody Usuario usuario
    ) {

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> usuarioExistenteOpt = repository.findById(id);
        if (usuarioExistenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioExistente = usuarioExistenteOpt.get();

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@") || !usuario.getEmail().contains(".")) {
            return ResponseEntity.badRequest().body(null);
        }
        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            return ResponseEntity.badRequest().body(null);
        }

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(usuario.getSenha());

        Usuario usuarioAtualizado = repository.save(usuarioExistente);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Usuario> usuarios = repository.findByNomeContainingIgnoreCase(nome);
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/filtro-email")
    public ResponseEntity<Usuario> buscarPorEmail(
            @RequestParam String email) {

        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = repository.findByEmail(email);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{id}/alterar-senha")
    public ResponseEntity<Void> alterarSenha(
            @PathVariable Integer id, @RequestBody Map<String, String> senhaRequest
    ) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        String novaSenha = senhaRequest.get("novaSenha");

        if (novaSenha == null || novaSenha.length() < 6) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> usuarioOpt = repository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(novaSenha);
        repository.save(usuario);

        return ResponseEntity.noContent().build();
    }
}
