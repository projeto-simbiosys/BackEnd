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
import school.sptech.Simbiosys.core.service.UsuarioService;
import school.sptech.Simbiosys.infrastructure.config.GerenciadorTokenJwt;
import school.sptech.Simbiosys.core.dto.UsuarioMapper;
import school.sptech.Simbiosys.core.dto.UsuarioRequestDto;
import school.sptech.Simbiosys.core.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.core.dto.UsuarioTokenDto;
import school.sptech.Simbiosys.core.application.exception.DadosInvalidosException;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;
import school.sptech.Simbiosys.infrastructure.persistence.repository.UsuarioRepository;

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

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setNome("Matheus Ferro");
        usuario.setEmail("matheus@email.com");
        usuario.setSenha("123456");

        when(usuarioRepository.existsByEmailIgnoreCaseContaining(dto.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuario);

        UsuarioEntity resultado = usuarioService.cadastrarUsuario(dto);

        assertNotNull(resultado);
        assertEquals("matheus@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Deve autenticar usuário com sucesso")
    void deveAutenticarUsuarioComSucesso() {
        UsuarioEntity usuario = new UsuarioEntity();
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
        UsuarioEntity usuario = new UsuarioEntity();
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
        UsuarioEntity usuario = new UsuarioEntity();
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

        UsuarioEntity usuarioExistente = new UsuarioEntity();
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

        UsuarioEntity usuarioAtualizado = new UsuarioEntity();
        usuarioAtualizado.setId(id);
        usuarioAtualizado.setNome(dto.getNome());
        usuarioAtualizado.setSobrenome(dto.getSobrenome());
        usuarioAtualizado.setCargo(dto.getCargo());
        usuarioAtualizado.setEmail(dto.getEmail());
        usuarioAtualizado.setSenha(dto.getSenha());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioAtualizado);

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

    @Test
    void deveRetornarListaVaziaQuandoNomeForNuloOuVazio() {
        List<UsuarioResponseDto> resultado1 = usuarioService.buscarPorNome(null);
        List<UsuarioResponseDto> resultado2 = usuarioService.buscarPorNome(" ");

        assertTrue(resultado1.isEmpty());
        assertTrue(resultado2.isEmpty());

        verify(usuarioRepository, never())
                .findByNomeContainingIgnoreCase(anyString());
    }

    @Test
    void deveBuscarPorEmailComSucesso() {
        String email = "joao@email.com";
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setNome("João");
        usuario.setEmail(email);

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        Optional<UsuarioResponseDto> resultado = usuarioService.buscarPorEmail(email);

        assertTrue(resultado.isPresent());
        assertEquals("João", resultado.get().getNome());

        verify(usuarioRepository, times(1))
                .findByEmail(email);
    }

    @Test
    void deveRetornarNullQuandoEmailInvalido() {
        Optional<UsuarioResponseDto> resultado1 = usuarioService.buscarPorEmail(null);
        Optional<UsuarioResponseDto> resultado2 = usuarioService.buscarPorEmail("   ");
        Optional<UsuarioResponseDto> resultado3 = usuarioService.buscarPorEmail("semarroba.com");

        assertNull(resultado1);
        assertNull(resultado2);
        assertNull(resultado3);

        verify(usuarioRepository, never())
                .findByEmail(anyString());
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        Integer id = 1;
        String novaSenha = "senhaNova";

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(id);
        usuario.setSenha("senhaAntiga");

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuario));

        boolean resultado = usuarioService.alterarSenha(id, novaSenha);

        assertTrue(resultado);
        assertEquals(novaSenha, usuario.getSenha());

        verify(usuarioRepository, times(1))
                .findById(id);
        verify(usuarioRepository, times(1))
                .save(usuario);
    }

    @Test
    void naoDeveAlterarSenhaQuandoIdOuSenhaForemInvalidos() {
        boolean resultado1 = usuarioService.alterarSenha(null, "senhaValida");
        boolean resultado2 = usuarioService.alterarSenha(0, "senhaValida");
        boolean resultado3 = usuarioService.alterarSenha(1, null);
        boolean resultado4 = usuarioService.alterarSenha(1, "123");

        assertFalse(resultado1);
        assertFalse(resultado2);
        assertFalse(resultado3);
        assertFalse(resultado4);

        verify(usuarioRepository, never()).findById(any());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void naoDeveAlterarSenhaSeUsuarioNaoExiste() {
        Integer id = 99;
        String novaSenha = "senhaValida";

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        boolean resultado = usuarioService.alterarSenha(id, novaSenha);

        assertFalse(resultado);

        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, never()).save(any());
    }
}
