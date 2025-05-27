package school.sptech.Simbiosys.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import school.sptech.Simbiosys.dto.UsuarioDetalhesDto;
import school.sptech.Simbiosys.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.exception.RelatorioNaoEncontradoException;
import school.sptech.Simbiosys.model.Relatorio;
import school.sptech.Simbiosys.model.Usuario;
import school.sptech.Simbiosys.repository.RelatorioRepository;
import school.sptech.Simbiosys.repository.UsuarioRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RelatorioServiceTest {

    @InjectMocks
    private RelatorioService service;
    @Mock
    private RelatorioRepository repository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private Authentication authentication;


    @BeforeEach
    void setUp() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Cadastrar quando mesAno não existe, deve cadastrar relatório")
    void cadastrarQuandoMesAnoNaoExisteDeveCadastrarRelatorioTest() {
        // arrange: configuração dos mocks
        Relatorio relatorio = new Relatorio();
        relatorio.setMesAno("01/2025");

        UsuarioDetalhesDto usuarioDetalhes = Mockito.mock(UsuarioDetalhesDto.class);
        Usuario usuario = new Usuario();

        // mock do authentication.getPrincipal() -> retorna o usuarioDetalhes
        Mockito.when(authentication.getPrincipal()).thenReturn(usuarioDetalhes);

        // mock do usuarioDetalhes.getEmail() -> retorna email falso
        Mockito.when(usuarioDetalhes.getEmail()).thenReturn("teste@teste.com");

        // mock do usuarioRepository.findByEmail() -> retorna o usuario
        Mockito.when(usuarioRepository.findByEmail("teste@teste.com")).thenReturn(Optional.of(usuario));

        // mock do existsByMesAno → false (não existe)
        Mockito.when(repository.existsByMesAno("01/2025")).thenReturn(false);

        // mock do save -> retorna o próprio relatório
        Mockito.when(repository.save(Mockito.any(Relatorio.class))).thenReturn(relatorio);

        // act: chama o método
        Relatorio salvo = service.cadastrar(relatorio, authentication);

        // assert: verificações
        assertNotNull(salvo);
        assertEquals("01/2025", salvo.getMesAno());

        Mockito.verify(publisher).publishEvent(Mockito.any());
        Mockito.verify(repository).save(Mockito.any(Relatorio.class));
    }


    @Test
    @DisplayName("Cadastrar relatório quando mesAno já existe, deve lançar exceção")
    void cadastrarQuandoMesAnoJaExisteDeveLancarExcecaoTest() {
        Relatorio relatorio = new Relatorio();
        relatorio.setMesAno("01/2025");

        Mockito.when(repository.existsByMesAno("01/2025")).thenReturn(true);

        assertThrows(EntidadeJaExistente.class,
                () -> service.cadastrar(relatorio, authentication)
        );
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Buscar por id válido quando existir deve retornar relatório")
    void buscarPorIdValidoQuandoExistirDeveRetornarRelatorioTest() {
        Relatorio relatorio = new Relatorio();
        relatorio.setId(1);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(relatorio));

        Relatorio resposta = service.buscarPorId(1);

        assertNotNull(resposta);
        assertEquals(1, resposta.getId());
    }

    @Test
    @DisplayName("Buscar por id inválido quando não existir deve lançar exceção")
    void buscarPorIdInvalidoQuandoNaoExistirDeveLancarExcecaoTest() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.buscarPorId(1)
        );
    }

    @Test
    @DisplayName("Remover relatório por id, quando acionado com id válido deve remover com sucesso")
    void removerRelatorioPorIdQuandoAcionadoComIdValidoDeveRemoverTest() {
        Mockito.when(repository.existsById(1)).thenReturn(true);
        Mockito.doNothing().when(repository).deleteById(1);

        service.deletar(1);

        Mockito.verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("Remover relatório por id, quando acionado com id inválido deve lançar exceção")
    void removerRelatorioPorIdQuandoAcionadoComIdInvalidoDeveLancarExcecaoTest() {
        Mockito.when(repository.existsById(1)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.deletar(1)
        );
        Mockito.verify(repository, Mockito.never()).deleteById(1);
    }

    @Test
    @DisplayName("Buscar por mesAno existente, quando acionado, deve retornar relatório")
    void buscarPorMesAnoExistenteQuandoAcionadoDeveRetornarRelatorioTest() {
        Relatorio relatorio = new Relatorio();
        relatorio.setMesAno("01/2025");

        Mockito.when(repository.existsByMesAno("01/2025")).thenReturn(true);
        Mockito.when(repository.findByMesAno("01/2025")).thenReturn(relatorio);

        Relatorio resposta = service.buscarPorMesAno("01/2025");

        assertNotNull(resposta);
        assertEquals("01/2025", resposta.getMesAno());
    }

    @Test
    @DisplayName("Buscar por mesAno inexistente, quando acionado, deve lançar exceção")
    void buscarPorMesAnoInexistenteQuandoAcionadoDeveLancarExcecaoTest() {
        Mockito.when(repository.existsByMesAno("01/2025")).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.buscarPorMesAno("01/2025")
        );
    }

    @Test
    @DisplayName("Somar relatórios por ano quando não existem relatórios deve retornar nulo")
    void somarRelatoriosPorAnoQuandoNaoExistemRelatoriosDeveRetornarNuloTest() {
        Mockito.when(repository.findByAno("2025")).thenReturn(Collections.emptyList());

        Relatorio resultado = service.somarRelatoriosPorAno("2025");

        assertNull(resultado);
    }

    @Test
    @DisplayName("Somar relatórios por período quando existem relatórios deve retornar relatório somado")
    void somarRelatoriosPorPeriodoQuandoExistemRelatoriosDeveRetornarRelatorioSomadoTest() {

        Relatorio relatorio1 = new Relatorio();
        relatorio1.setMesAno("01/2025");

        Relatorio relatorio2 = new Relatorio();
        relatorio2.setMesAno("02/2025");

        Mockito.when(repository.findByPeriodo("01/2025", "06/2025")).thenReturn(List.of(relatorio1, relatorio2));

        Relatorio resultado = service.somarRelatoriosPorPeriodo("01/2025", "06/2025");

        assertNotNull(resultado);
        assertEquals("01/2025 até 06/2025", resultado.getMesAno());
    }

    @Test
    @DisplayName("Somar relatórios por período quando não existe deve lançar exceção")
    void somarRelatoriosPorPeriodoQuandoNaoExisteDeveLancarExcecaoTest() {
        Mockito.when(repository.findByPeriodo("01/2025", "06/2025")).thenReturn(Collections.emptyList());

        assertThrows(RelatorioNaoEncontradoException.class,
                () -> service.somarRelatoriosPorPeriodo("01/2025", "06/2025")
        );
    }

    @Test
    @DisplayName("Atualizar quando relatório existe deve atualizar campos não nulos")
    void atualizarQuandoRelatorioExisteDeveAtualizarCamposNaoNulosTest() {
        // mock do authentication
        Authentication authentication = Mockito.mock(Authentication.class);
        UsuarioDetalhesDto usuarioDetalhes = Mockito.mock(UsuarioDetalhesDto.class);
        Mockito.when(usuarioDetalhes.getEmail()).thenReturn("usuario@teste.com");
        Mockito.when(authentication.getPrincipal()).thenReturn(usuarioDetalhes);

        //mock do usuário
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@teste.com");

        Mockito.when(usuarioRepository.findByEmail("usuario@teste.com"))
                .thenReturn(Optional.of(usuario));

        Relatorio existente = new Relatorio();
        existente.setId(1);
        existente.setMesAno("01/2025");

        // input
        Relatorio input = new Relatorio();
        input.setMesAno("02/2025");

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(existente));
        Mockito.when(repository.save(Mockito.any(Relatorio.class))).thenReturn(existente);

        // executa
        Relatorio atualizado = service.atualizar(1, input, authentication);

        // validação
        assertNotNull(atualizado);
        assertEquals("02/2025", atualizado.getMesAno());

        // verifica que o save foi chamado
        Mockito.verify(repository).save(Mockito.any(Relatorio.class));
    }

    @Test
    @DisplayName("Atualizar quando relatório não existe deve lançar exceção")
    void atualizarQuandoRelatorioNaoExisteDeveLancarExcecaoTest() {
        Relatorio input = new Relatorio();

        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.atualizar(1, input, authentication)
        );
    }

}