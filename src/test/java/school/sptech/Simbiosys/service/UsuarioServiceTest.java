package school.sptech.Simbiosys.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.Simbiosys.config.GerenciadorTokenJwt;
import school.sptech.Simbiosys.dto.UsuarioMapper;
import school.sptech.Simbiosys.dto.UsuarioRequestDto;
import school.sptech.Simbiosys.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.dto.UsuarioTokenDto;
import school.sptech.Simbiosys.exception.DadosInvalidosException;
import school.sptech.Simbiosys.model.Usuario;
import school.sptech.Simbiosys.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Deve retornar lista vazia quando não existirem usuários cadastrados")
    void deveRetornarListaVaziaQuandoNaoExistiremUsuarios(){

        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<UsuarioResponseDto> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar usuário com nome inválido")
    void deveLancarExcecaoQuandoNomeInvalidoAoCadastrar() {
        UsuarioRequestDto dto = new UsuarioRequestDto();
        dto.setNome("");
        dto.setEmail("teste@email.com");
        dto.setSenha("123456");
        dto.setToken("#ACESSO-CEFOPEA");

        assertThrows(DadosInvalidosException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar usuário com e-mail inválido")
    void deveLancarExcecaoQuandoEmailInvalidoAoCadastrar() {
        UsuarioRequestDto dto = new UsuarioRequestDto();
        dto.setNome("Cintia");
        dto.setEmail("cinta@emailcom");
        dto.setSenha("123456");
        dto.setToken("#ACESSO-CEFOPEA");

        assertThrows(DadosInvalidosException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar usuário com token inválido")
    void deveLancarExcecaoQuandoTokenInvalidoAoCadastrar() {
        UsuarioRequestDto dto = new UsuarioRequestDto();
        dto.setNome("Cintia");
        dto.setEmail("cinta@email.com");
        dto.setSenha("12345");
        dto.setToken("CEFOPEA");

        assertThrows(DadosInvalidosException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar usuário com senha inválida")
    void deveLancarExcecaoQuandoSenhaInvalidoAoCadastrar() {
        UsuarioRequestDto dto = new UsuarioRequestDto();
        dto.setNome("Cintia");
        dto.setEmail("cinta@email.com");
        dto.setSenha("12345");
        dto.setToken("#ACESSO-CEFOPEA");

        assertThrows(DadosInvalidosException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });
    }

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void deveCadastrarUsuarioComSucesso() {
        UsuarioRequestDto dto = new UsuarioRequestDto();
        dto.setNome("Matheus Ferro");
        dto.setEmail("matheus@email.com");
        dto.setSenha("123456");
        dto.setToken("#ACESSO-CEFOPEA");

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Matheus Ferro");
        usuario.setEmail("matheus@email.com");
        usuario.setSenha("123456");

        when(usuarioRepository.existsByEmailIgnoreCaseContaining(dto.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.cadastrarUsuario(dto);

        assertNotNull(resultado);
        assertEquals("matheus@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Deve autenticar usuário com sucesso")
    void deveAutenticarUsuarioComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setEmail("reynald@email.com");
        usuario.setSenha("123456");

        Authentication authMock = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);

        when(usuarioRepository.findByEmail("reynald@email.com"))
                .thenReturn(Optional.of(usuario));

        when(gerenciadorTokenJwt.generateToken(authMock)).thenReturn("token123");

        UsuarioTokenDto expectedTokenDto = new UsuarioTokenDto(1, "Reynald", "reynald@email.com", "token123");
        mockStatic(UsuarioMapper.class).when(() -> UsuarioMapper.of(usuario, "token123")).thenReturn(expectedTokenDto);

        UsuarioTokenDto result = usuarioService.autenticar(usuario);

        assertNotNull(result);
        assertEquals("reynald@email.com", result.getEmail());
        assertEquals("token123", result.getToken());
    }

    @Test
    @DisplayName("Deve lançar exceção ao falhar autenticação do usuário")
    void deveLancarExcecaoQuandoAutenticacaoFalhar() {
        Usuario usuario = new Usuario();
        usuario.setEmail("cintia@email.com");
        usuario.setSenha("123456");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            usuarioService.autenticar(usuario);
        });

        assertEquals("Credenciais inválidas", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar usuário existente por ID")
    void deveRetornarUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Vitoria");
        usuario.setEmail("vitoria@email.com");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<UsuarioResponseDto> resultado = usuarioService.buscarUsuarioPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Vitoria", resultado.get().getNome());
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void deveAtualizarUsuarioComSucesso() {
        Integer id = 1;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);
        usuarioExistente.setNome("Antigo");
        usuarioExistente.setEmail("antigo@email.com");
        usuarioExistente.setSenha("123456");

        UsuarioRequestDto dto = new UsuarioRequestDto();
        dto.setNome("Novo Nome");
        dto.setSobrenome("Novo Sobrenome");
        dto.setCargo("Novo Cargo");
        dto.setEmail("novo@email.com");
        dto.setSenha("654321");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(id);
        usuarioAtualizado.setNome(dto.getNome());
        usuarioAtualizado.setSobrenome(dto.getSobrenome());
        usuarioAtualizado.setCargo(dto.getCargo());
        usuarioAtualizado.setEmail(dto.getEmail());
        usuarioAtualizado.setSenha(dto.getSenha());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioAtualizado);

        Optional<UsuarioResponseDto> resultado = usuarioService.atualizarUsuario(id, dto);

        assertTrue(resultado.isPresent());
        assertEquals("Novo Nome", resultado.get().getNome());
        assertEquals("novo@email.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("Deve deletar usuário existente com sucesso")
    void deveDeletarUsuarioExistente() {
        when(usuarioRepository.existsById(1)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1);

        boolean resultado = usuarioService.deletarUsuario(1);

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Não deve deletar usuário inexistente")
    void naoDeveDeletarUsuarioInexistente() {
        when(usuarioRepository.existsById(999)).thenReturn(false);

        boolean resultado = usuarioService.deletarUsuario(999);

        assertFalse(resultado);
    }
}
