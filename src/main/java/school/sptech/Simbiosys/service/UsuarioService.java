package school.sptech.Simbiosys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.controller.dto.UsuarioRequestDto;
import school.sptech.Simbiosys.controller.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.exception.UsuarioException;
import school.sptech.Simbiosys.model.Usuario;
import school.sptech.Simbiosys.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    private List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario cadastrarUsuario(UsuarioRequestDto dto) {

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new UsuarioException("O nome é obrigatório.");
        }

        if (dto.getEmail() == null || !dto.getEmail().contains("@") || !dto.getEmail().contains(".")) {
            throw new UsuarioException("Email inválido.");
        }

        if (dto.getSenha() == null || dto.getSenha().length() < 6) {
            throw new UsuarioException("A senha deve ter pelo menos 6 caracteres.");
        }

        if (usuarioRepository.existsByEmailIgnoreCaseContaining(dto.getEmail())) {
            throw new UsuarioException("Já existe um usuário cadastrado com este email.");
        }


        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getSenha());
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(encryptedPassword);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        usuarios.add(usuarioSalvo);

        return usuarioSalvo;
    }

    public Optional<Usuario> buscarUsuarioPorId(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return usuarioRepository.findById(id);
    }

    public boolean deletarUsuario(Integer id) {
        if (id == null || id <= 0 || !usuarioRepository.existsById(id)) {
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }

    public Optional<UsuarioResponseDto> atualizarUsuario(Integer id, UsuarioRequestDto dto) {
        if (id == null || id <= 0 || dto == null) return Optional.empty();

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) return Optional.empty();

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()
                || dto.getEmail() == null || !dto.getEmail().contains("@")
                || dto.getSenha() == null || dto.getSenha().length() < 6) {
            return Optional.empty();
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        Usuario salvo = usuarioRepository.save(usuario);
        return Optional.of(new UsuarioResponseDto(salvo));
    }

    public List<Usuario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return null;
        }
        return usuarioRepository.findByEmail(email);
    }

    public boolean alterarSenha(Integer id, String novaSenha) {
        if (id == null || id <= 0 || novaSenha == null || novaSenha.length() < 6) {
            return false;
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
        return true;
    }

}
