package school.sptech.Simbiosys.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.Simbiosys.infrastructure.config.GerenciadorTokenJwt;
import school.sptech.Simbiosys.core.dto.UsuarioMapper;
import school.sptech.Simbiosys.core.dto.UsuarioRequestDto;
import school.sptech.Simbiosys.core.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.core.dto.UsuarioTokenDto;
import school.sptech.Simbiosys.core.application.exception.DadosInvalidosException;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;
import school.sptech.Simbiosys.infrastructure.persistence.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;


    private List<UsuarioEntity> usuarios = new ArrayList<>();

    public List<UsuarioResponseDto> listarUsuarios() {
        List<UsuarioEntity> usuariosEncontrados = usuarioRepository.findAll();
        return usuariosEncontrados.stream().map(UsuarioMapper::of).toList();
    }

    public UsuarioEntity cadastrarUsuario(UsuarioRequestDto dto) {

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new DadosInvalidosException("O nome é obrigatório.");
        }

        if (dto.getEmail() == null || !dto.getEmail().contains("@") || !dto.getEmail().contains(".")) {
            throw new DadosInvalidosException("Email inválido.");
        }

        if (dto.getSenha() == null || dto.getSenha().length() < 6) {
            throw new DadosInvalidosException("A senha deve ter pelo menos 6 caracteres.");
        }

        if (usuarioRepository.existsByEmailIgnoreCaseContaining(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário cadastrado com este email.");
        }

        if (!dto.getToken().equals("#ACESSO-CEFOPEA")) {
            throw new DadosInvalidosException("Token para cadastro inválido.");
        }


        UsuarioEntity usuarioSalvo = usuarioRepository.save(UsuarioMapper.of(dto));
        usuarios.add(usuarioSalvo);

        return usuarioSalvo;
    }

    public UsuarioTokenDto autenticar(UsuarioEntity usuario) {
        System.out.println("SENHA RECEBIDA: " + usuario.getSenha());
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(), usuario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        UsuarioEntity usuarioAutenticado =
                usuarioRepository.findByEmail(usuario.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Optional<UsuarioResponseDto> buscarUsuarioPorId(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return usuarioRepository.findById(id)
                .map(UsuarioMapper::of);
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

        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) return Optional.empty();

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()
                || dto.getEmail() == null || !dto.getEmail().contains("@")
                || dto.getSenha() == null || dto.getSenha().length() < 6) {
            return Optional.empty();
        }
        UsuarioEntity usuario = usuarioOpt.get();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getSenha());

        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setCargo(dto.getCargo());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(encriptandoSenha(dto.getSenha()));

        UsuarioEntity salvo = usuarioRepository.save(usuario);
        return Optional.of(new UsuarioResponseDto(salvo));
    }

    public List<UsuarioResponseDto> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<UsuarioEntity> usuariosEncontrados = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        return usuariosEncontrados.stream().map(UsuarioMapper::of).toList();
    }

    public Optional<UsuarioResponseDto> buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return null;
        }
        return usuarioRepository.findByEmail(email)
                .map(UsuarioMapper::of);
    }

    public boolean alterarSenha(Integer id, String novaSenha) {
        if (id == null || id <= 0 || novaSenha == null || novaSenha.length() < 6) {
            return false;
        }

        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return false;
        }

        UsuarioEntity usuario = usuarioOpt.get();
        usuario.setSenha(encriptandoSenha(novaSenha));
        usuarioRepository.save(usuario);
        return true;
    }

    public static String encriptandoSenha(String senha){
        String encryptedPassword = new BCryptPasswordEncoder().encode(senha);
        return encryptedPassword;
    }
}
