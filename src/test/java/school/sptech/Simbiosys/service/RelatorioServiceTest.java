package school.sptech.Simbiosys.service;

import org.junit.jupiter.api.*;
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
import school.sptech.Simbiosys.model.*;
import school.sptech.Simbiosys.repository.RelatorioRepository;
import school.sptech.Simbiosys.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        SecurityContext securityContext = mock(SecurityContext.class);
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

        UsuarioDetalhesDto usuarioDetalhes = mock(UsuarioDetalhesDto.class);
        Usuario usuario = new Usuario();

        // mock do authentication.getPrincipal() -> retorna o usuarioDetalhes
        when(authentication.getPrincipal()).thenReturn(usuarioDetalhes);

        // mock do usuarioDetalhes.getEmail() -> retorna email falso
        when(usuarioDetalhes.getEmail()).thenReturn("teste@teste.com");

        // mock do usuarioRepository.findByEmail() -> retorna o usuario
        when(usuarioRepository.findByEmail("teste@teste.com")).thenReturn(Optional.of(usuario));

        // mock do existsByMesAno → false (não existe)
        when(repository.existsByMesAno("01/2025")).thenReturn(false);

        // mock do save -> retorna o próprio relatório
        when(repository.save(any(Relatorio.class))).thenReturn(relatorio);

        // act: chama o método
        Relatorio salvo = service.cadastrar(relatorio, authentication);

        // assert: verificações
        assertNotNull(salvo);
        assertEquals("01/2025", salvo.getMesAno());

        verify(publisher).publishEvent(any());
        verify(repository).save(any(Relatorio.class));
    }


    @Test
    @DisplayName("Cadastrar relatório quando mesAno já existe, deve lançar exceção")
    void cadastrarQuandoMesAnoJaExisteDeveLancarExcecaoTest() {
        Relatorio relatorio = new Relatorio();
        relatorio.setMesAno("01/2025");

        when(repository.existsByMesAno("01/2025")).thenReturn(true);

        assertThrows(EntidadeJaExistente.class,
                () -> service.cadastrar(relatorio, authentication)
        );
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Buscar por id válido quando existir deve retornar relatório")
    void buscarPorIdValidoQuandoExistirDeveRetornarRelatorioTest() {
        Relatorio relatorio = new Relatorio();
        relatorio.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(relatorio));

        Relatorio resposta = service.buscarPorId(1);

        assertNotNull(resposta);
        assertEquals(1, resposta.getId());
    }

    @Test
    @DisplayName("Buscar por id inválido quando não existir deve lançar exceção")
    void buscarPorIdInvalidoQuandoNaoExistirDeveLancarExcecaoTest() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.buscarPorId(1)
        );
    }

    @Test
    @DisplayName("Remover relatório por id, quando acionado com id válido deve remover com sucesso")
    void removerRelatorioPorIdQuandoAcionadoComIdValidoDeveRemoverTest() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        service.deletar(1);

        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("Remover relatório por id, quando acionado com id inválido deve lançar exceção")
    void removerRelatorioPorIdQuandoAcionadoComIdInvalidoDeveLancarExcecaoTest() {
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.deletar(1)
        );
        verify(repository, never()).deleteById(1);
    }

    @Test
    @DisplayName("Buscar por mesAno existente, quando acionado, deve retornar relatório")
    void buscarPorMesAnoExistenteQuandoAcionadoDeveRetornarRelatorioTest() {
        Relatorio relatorio = new Relatorio();
        relatorio.setMesAno("01/2025");

        when(repository.existsByMesAno("01/2025")).thenReturn(true);
        when(repository.findByMesAno("01/2025")).thenReturn(relatorio);

        Relatorio resposta = service.buscarPorMesAno("01/2025");

        assertNotNull(resposta);
        assertEquals("01/2025", resposta.getMesAno());
    }

    @Test
    @DisplayName("Buscar por mesAno inexistente, quando acionado, deve lançar exceção")
    void buscarPorMesAnoInexistenteQuandoAcionadoDeveLancarExcecaoTest() {
        when(repository.existsByMesAno("01/2025")).thenReturn(false);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.buscarPorMesAno("01/2025")
        );
    }

    @Test
    @DisplayName("Somar relatórios por ano quando não existem relatórios deve retornar nulo")
    void somarRelatoriosPorAnoQuandoNaoExistemRelatoriosDeveRetornarNuloTest() {
        when(repository.findByAno("2025")).thenReturn(Collections.emptyList());

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

        when(repository.findByPeriodo("01/2025", "06/2025")).thenReturn(List.of(relatorio1, relatorio2));

        Relatorio resultado = service.somarRelatoriosPorPeriodo("01/2025", "06/2025");

        assertNotNull(resultado);
        assertEquals("01/2025 até 06/2025", resultado.getMesAno());
    }

    @Test
    @DisplayName("Somar relatórios por período quando não existe deve lançar exceção")
    void somarRelatoriosPorPeriodoQuandoNaoExisteDeveLancarExcecaoTest() {
        when(repository.findByPeriodo("01/2025", "06/2025")).thenReturn(Collections.emptyList());

        assertThrows(RelatorioNaoEncontradoException.class,
                () -> service.somarRelatoriosPorPeriodo("01/2025", "06/2025")
        );
    }

    @Test
    @DisplayName("Somar relatórios por ano quando existir deve retornar relatório somado")
    void somarRelatoriosPorAnoDeveRetornarRelatorioSomado() {
        Relatorio relatorio1 = new Relatorio();
        relatorio1.setMesAno("01/2024");
        relatorio1.setEncaminhamento(new Encaminhamento());
        relatorio1.getEncaminhamento().setEncSaude(5);
        relatorio1.setOutrosNumeros(new OutrosNumeros());
        relatorio1.getOutrosNumeros().setAlimentacao(10);
        relatorio1.setAcoesRealizadas(new AcoesRealizadas());
        relatorio1.getAcoesRealizadas().setTotalPalestrasPresenciais(2);

        Relatorio relatorio2 = new Relatorio();
        relatorio2.setMesAno("02/2024");
        relatorio2.setEncaminhamento(new Encaminhamento());
        relatorio2.getEncaminhamento().setEncSaude(3);
        relatorio2.setOutrosNumeros(new OutrosNumeros());
        relatorio2.getOutrosNumeros().setAlimentacao(7);
        relatorio2.setAcoesRealizadas(new AcoesRealizadas());
        relatorio2.getAcoesRealizadas().setTotalPalestrasPresenciais(4);

        List<Relatorio> relatorios = List.of(relatorio1, relatorio2);

        when(repository.findByAno("2024")).thenReturn(relatorios);

        Relatorio resultado = service.somarRelatoriosPorAno("2024");

        assertNotNull(resultado);
        assertEquals("Ano 2024", resultado.getMesAno());
        assertEquals(8, resultado.getEncaminhamento().getEncSaude());
        assertEquals(17, resultado.getOutrosNumeros().getAlimentacao());
        assertEquals(6, resultado.getAcoesRealizadas().getTotalPalestrasPresenciais());
    }


    @Test
    @DisplayName("Atualizar quando relatório existe deve atualizar campos não nulos")
    void atualizarQuandoRelatorioExisteDeveAtualizarCamposNaoNulosTest() {

        Authentication authentication = mock(Authentication.class);
        UsuarioDetalhesDto usuarioDetalhes = mock(UsuarioDetalhesDto.class);
        when(usuarioDetalhes.getEmail()).thenReturn("usuario@teste.com");
        when(authentication.getPrincipal()).thenReturn(usuarioDetalhes);

        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@teste.com");

        when(usuarioRepository.findByEmail("usuario@teste.com"))
                .thenReturn(Optional.of(usuario));

        Relatorio existente = new Relatorio();
        existente.setId(1);
        existente.setMesAno("01/2025");

        Relatorio input = new Relatorio();
        input.setMesAno("02/2025");

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(any(Relatorio.class))).thenReturn(existente);

        Relatorio atualizado = service.atualizar(1, input, authentication);

        assertNotNull(atualizado);
        assertEquals("02/2025", atualizado.getMesAno());

        verify(repository).save(any(Relatorio.class));
    }

    @Test
    @DisplayName("Atualizar quando relatório não existe deve lançar exceção")
    void atualizarQuandoRelatorioNaoExisteDeveLancarExcecaoTest() {
        Relatorio input = new Relatorio();

        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.atualizar(1, input, authentication)
        );
    }

    @Test
    void deveBuscarRelatoriosPorAno() {
        String ano = "2024";
        Relatorio relatorio1 = new Relatorio();
        relatorio1.setId(1);
        relatorio1.setMesAno("01/2024");

        Relatorio relatorio2 = new Relatorio();
        relatorio2.setId(2);
        relatorio2.setMesAno("02/2024");

        List<Relatorio> listaMock = List.of(relatorio1, relatorio2);

        when(repository.findByAno(ano)).thenReturn(listaMock);

        List<Relatorio> resultado = service.buscarRelatoriosPorAno(ano);

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("01/2024", resultado.get(0).getMesAno());
        Assertions.assertEquals("02/2024", resultado.get(1).getMesAno());

        verify(repository, times(1)).findByAno(ano);
    }

    @Test
    void deveBuscarRelatoriosPorPeriodo() {
        String de = "01/2024";
        String para = "06/2024";

        Relatorio relatorio1 = new Relatorio();
        relatorio1.setId(1);
        relatorio1.setMesAno("02/2024");

        Relatorio relatorio2 = new Relatorio();
        relatorio2.setId(2);
        relatorio2.setMesAno("05/2024");

        List<Relatorio> listaMock = List.of(relatorio1, relatorio2);

        when(repository.findByPeriodo(de, para)).thenReturn(listaMock);

        List<Relatorio> resultado = service.buscarRelatoriosPorPeriodo(de, para);

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("02/2024", resultado.get(0).getMesAno());
        Assertions.assertEquals("05/2024", resultado.get(1).getMesAno());

        verify(repository, times(1)).findByPeriodo(de, para);
    }

    @Test
    void deveAtualizarRelatorioComSucesso() {
        // Arrange - setup dos dados existentes
        Integer id = 1;
        Relatorio existente = new Relatorio();
        existente.setId(id);
        existente.setMesAno("01/2024");
        existente.setAberto(true);

        // Encaminhamento existente
        existente.setEncaminhamento(new Encaminhamento());
        existente.getEncaminhamento().setEncSaude(1);

        // Dados de entrada para atualização
        Relatorio input = new Relatorio();
        input.setMesAno("02/2024");
        input.setAberto(false);

        Encaminhamento encaminhamento = new Encaminhamento();
        encaminhamento.setEncSaude(5);
        input.setEncaminhamento(encaminhamento);

        OutrosNumeros outros = new OutrosNumeros();
        outros.setAlimentacao(10);
        input.setOutrosNumeros(outros);

        AcoesRealizadas acoes = new AcoesRealizadas();
        acoes.setTotalAtividadesGrupoVirtual(20);
        input.setAcoesRealizadas(acoes);

        // Mock do authentication e usuarioDetalhes
        UsuarioDetalhesDto usuarioDetalhes = mock(UsuarioDetalhesDto.class);
        when(authentication.getPrincipal()).thenReturn(usuarioDetalhes);
        when(usuarioDetalhes.getEmail()).thenReturn("usuario@teste.com");

        // Mock do usuarioRepository
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@teste.com");
        when(usuarioRepository.findByEmail("usuario@teste.com")).thenReturn(Optional.of(usuario));

        // Mock do repository
        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(Relatorio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Relatorio atualizado = service.atualizar(id, input, authentication);

        // Assert - verificar se atualizou corretamente
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals("02/2024", atualizado.getMesAno());
        Assertions.assertEquals(false, atualizado.getAberto());

        Assertions.assertEquals(5, atualizado.getEncaminhamento().getEncSaude());
        Assertions.assertEquals(10, atualizado.getOutrosNumeros().getAlimentacao());
        Assertions.assertEquals(20, atualizado.getAcoesRealizadas().getTotalAtividadesGrupoVirtual());

        // Verificar se salvou (se tiver save no método)
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(Relatorio.class));
    }
}