package school.sptech.Simbiosys.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.core.application.usecase.usuarioUseCase.*;
import school.sptech.Simbiosys.core.dto.*;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;
import school.sptech.Simbiosys.core.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "${cors.allowed.origin}")
public class UsuarioController {

    @Autowired
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    @Autowired
    private ListarUsuariosUseCase listarUsuariosUseCase;
    @Autowired
    private AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    @Autowired
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    @Autowired
    private DeletarUsuarioUseCase deletarUsuarioUseCase;
    @Autowired
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    @Autowired
    private BuscarPorNomeUsuarioUseCase buscarPorNomeUsuarioUseCase;
    @Autowired
    private BuscarPorEmailUsuarioUseCase buscarPorEmailUsuarioUseCase;
    @Autowired
    private AlterarSenhaUsuarioUseCase alterarSenhaUsuarioUseCase;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrarUsuario(@RequestBody UsuarioRequestDto dto) {
        try {
            UsuarioEntity usuarioCadastrado = cadastrarUsuarioUseCase.execute(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.of(usuarioCadastrado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        final UsuarioEntity usuario = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto usuarioTokenDto = autenticarUsuarioUseCase.execute(usuario);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        List<UsuarioResponseDto> usuarios = listarUsuariosUseCase.execute();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Integer id) {
        return buscarUsuarioPorIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        boolean deletado = deletarUsuarioUseCase.execute(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Integer id,
                                                        @RequestBody UsuarioRequestDto dto) {
        Optional<UsuarioResponseDto> atualizado = atualizarUsuarioUseCase.execute(id, dto);
        return atualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<UsuarioResponseDto>> buscarPorNome(@RequestParam String nome) {
        List<UsuarioResponseDto> usuarios = buscarPorNomeUsuarioUseCase.execute(nome);
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/filtro-email")
    public ResponseEntity<Optional<UsuarioResponseDto>> buscarPorEmail(@RequestParam String email) {
        Optional<UsuarioResponseDto> usuario = buscarPorEmailUsuarioUseCase.execute(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{id}/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@PathVariable Integer id,
                                             @RequestBody AlterarSenhaDto dto) {
        boolean ok = alterarSenhaUsuarioUseCase.execute(id, dto.getNovaSenha());
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }
}
