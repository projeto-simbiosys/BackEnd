package school.sptech.Simbiosys.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import school.sptech.Simbiosys.core.service.RelatorioService;
import school.sptech.Simbiosys.core.dto.UsuarioDetalhesDto;
import school.sptech.Simbiosys.core.application.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.core.application.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.core.application.exception.RelatorioNaoEncontradoException;
import school.sptech.Simbiosys.infrastructure.persistence.entity.*;
import school.sptech.Simbiosys.infrastructure.persistence.repository.RelatorioRepository;
import school.sptech.Simbiosys.infrastructure.persistence.repository.UsuarioRepository;

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
        RelatorioEntity relatorio = new RelatorioEntity();
        relatorio.setMesAno("01/2025");

        UsuarioDetalhesDto usuarioDetalhes = mock(UsuarioDetalhesDto.class);
        UsuarioEntity usuario = new UsuarioEntity();

        when(authentication.getPrincipal()).thenReturn(usuarioDetalhes);
        when(usuarioDetalhes.getEmail()).thenReturn("teste@teste.com");
        when(usuarioRepository.findByEmail("teste@teste.com")).thenReturn(Optional.of(usuario));
        when(repository.existsByMesAno("01/2025")).thenReturn(false);
        when(repository.save(any(RelatorioEntity.class))).thenReturn(relatorio);

        // act: chama o método
        RelatorioEntity salvo = service.cadastrar(relatorio, authentication);

        // assert: verificações
        assertNotNull(salvo);
        assertEquals("01/2025", salvo.getMesAno());

        verify(repository).save(any(RelatorioEntity.class));
    }


    @Test
    @DisplayName("Cadastrar relatório quando mesAno já existe, deve lançar exceção")
    void cadastrarQuandoMesAnoJaExisteDeveLancarExcecaoTest() {
        RelatorioEntity relatorio = new RelatorioEntity();
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
        RelatorioEntity relatorio = new RelatorioEntity();
        relatorio.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(relatorio));

        RelatorioEntity resposta = service.buscarPorId(1);

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
        RelatorioEntity relatorio = new RelatorioEntity();
        relatorio.setMesAno("01/2025");

        when(repository.existsByMesAno("01/2025")).thenReturn(true);
        when(repository.findByMesAno("01/2025")).thenReturn(relatorio);

        RelatorioEntity resposta = service.buscarPorMesAno("01/2025");

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

        RelatorioEntity resultado = service.somarRelatoriosPorAno("2025");

        assertNull(resultado);
    }

    @Test
    @DisplayName("Somar relatórios por período quando existem relatórios deve retornar relatório somado")
    void somarRelatoriosPorPeriodoQuandoExistemRelatoriosDeveRetornarRelatorioSomadoTest() {

        RelatorioEntity relatorio1 = new RelatorioEntity();
        relatorio1.setMesAno("01/2025");

        RelatorioEntity relatorio2 = new RelatorioEntity();
        relatorio2.setMesAno("02/2025");

        when(repository.findByPeriodo("01/2025", "06/2025")).thenReturn(List.of(relatorio1, relatorio2));

        RelatorioEntity resultado = service.somarRelatoriosPorPeriodo("01/2025", "06/2025");

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
        RelatorioEntity relatorio1 = new RelatorioEntity();
        relatorio1.setMesAno("01/2024");
        relatorio1.setEncaminhamento(new EncaminhamentoEntity());
        relatorio1.getEncaminhamento().setEncSaude(5);
        relatorio1.setOutrosNumeros(new OutrosNumerosEntity());
        relatorio1.getOutrosNumeros().setAlimentacao(10);
        relatorio1.setAcoesRealizadas(new AcoesRealizadasEntity());
        relatorio1.getAcoesRealizadas().setTotalPalestrasPresenciais(2);

        RelatorioEntity relatorio2 = new RelatorioEntity();
        relatorio2.setMesAno("02/2024");
        relatorio2.setEncaminhamento(new EncaminhamentoEntity());
        relatorio2.getEncaminhamento().setEncSaude(3);
        relatorio2.setOutrosNumeros(new OutrosNumerosEntity());
        relatorio2.getOutrosNumeros().setAlimentacao(7);
        relatorio2.setAcoesRealizadas(new AcoesRealizadasEntity());
        relatorio2.getAcoesRealizadas().setTotalPalestrasPresenciais(4);

        List<RelatorioEntity> relatorios = List.of(relatorio1, relatorio2);

        when(repository.findByAno("2024")).thenReturn(relatorios);

        RelatorioEntity resultado = service.somarRelatoriosPorAno("2024");

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

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setEmail("usuario@teste.com");

        when(usuarioRepository.findByEmail("usuario@teste.com"))
                .thenReturn(Optional.of(usuario));

        RelatorioEntity existente = new RelatorioEntity();
        existente.setId(1);
        existente.setMesAno("01/2025");

        RelatorioEntity input = new RelatorioEntity();
        input.setMesAno("02/2025");

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(any(RelatorioEntity.class))).thenReturn(existente);

        RelatorioEntity atualizado = service.atualizar(1, input, authentication);

        assertNotNull(atualizado);
        assertEquals("02/2025", atualizado.getMesAno());

        verify(repository).save(any(RelatorioEntity.class));
    }

    @Test
    @DisplayName("Atualizar quando relatório não existe deve lançar exceção")
    void atualizarQuandoRelatorioNaoExisteDeveLancarExcecaoTest() {
        RelatorioEntity input = new RelatorioEntity();

        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> service.atualizar(1, input, authentication)
        );
    }

    @Test
    void deveBuscarRelatoriosPorAno() {
        String ano = "2024";
        RelatorioEntity relatorio1 = new RelatorioEntity();
        relatorio1.setId(1);
        relatorio1.setMesAno("01/2024");

        RelatorioEntity relatorio2 = new RelatorioEntity();
        relatorio2.setId(2);
        relatorio2.setMesAno("02/2024");

        List<RelatorioEntity> listaMock = List.of(relatorio1, relatorio2);

        when(repository.findByAno(ano)).thenReturn(listaMock);

        List<RelatorioEntity> resultado = service.buscarRelatoriosPorAno(ano);

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("01/2024", resultado.get(0).getMesAno());
        Assertions.assertEquals("02/2024", resultado.get(1).getMesAno());

        verify(repository, times(1)).findByAno(ano);
    }

    @Test
    void deveBuscarRelatoriosPorPeriodo() {
        String de = "01/2024";
        String para = "06/2024";

        RelatorioEntity relatorio1 = new RelatorioEntity();
        relatorio1.setId(1);
        relatorio1.setMesAno("02/2024");

        RelatorioEntity relatorio2 = new RelatorioEntity();
        relatorio2.setId(2);
        relatorio2.setMesAno("05/2024");

        List<RelatorioEntity> listaMock = List.of(relatorio1, relatorio2);

        when(repository.findByPeriodo(de, para)).thenReturn(listaMock);

        List<RelatorioEntity> resultado = service.buscarRelatoriosPorPeriodo(de, para);

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("02/2024", resultado.get(0).getMesAno());
        Assertions.assertEquals("05/2024", resultado.get(1).getMesAno());

        verify(repository, times(1)).findByPeriodo(de, para);
    }

    @Test
    void deveAtualizarRelatorioComSucesso() {
        // Arrange - setup dos dados existentes
        Integer id = 1;
        RelatorioEntity existente = new RelatorioEntity();
        existente.setId(id);
        existente.setMesAno("01/2024");
        existente.setAberto(true);

        // EncaminhamentoEntity existente
        existente.setEncaminhamento(new EncaminhamentoEntity());
        existente.getEncaminhamento().setEncSaude(1);

        // Dados de entrada para atualização
        RelatorioEntity input = new RelatorioEntity();
        input.setMesAno("02/2024");
        input.setAberto(false);

        EncaminhamentoEntity encaminhamento = new EncaminhamentoEntity();
        encaminhamento.setEncSaude(5);
        input.setEncaminhamento(encaminhamento);

        OutrosNumerosEntity outros = new OutrosNumerosEntity();
        outros.setAlimentacao(10);
        input.setOutrosNumeros(outros);

        AcoesRealizadasEntity acoes = new AcoesRealizadasEntity();
        acoes.setTotalAtividadesGrupoVirtual(20);
        input.setAcoesRealizadas(acoes);

        // Mock do authentication e usuarioDetalhes
        UsuarioDetalhesDto usuarioDetalhes = mock(UsuarioDetalhesDto.class);
        when(authentication.getPrincipal()).thenReturn(usuarioDetalhes);
        when(usuarioDetalhes.getEmail()).thenReturn("usuario@teste.com");

        // Mock do usuarioRepository
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setEmail("usuario@teste.com");
        when(usuarioRepository.findByEmail("usuario@teste.com")).thenReturn(Optional.of(usuario));

        // Mock do repository
        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(RelatorioEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RelatorioEntity atualizado = service.atualizar(id, input, authentication);

        // Assert - verificar se atualizou corretamente
        Assertions.assertNotNull(atualizado);
        Assertions.assertEquals("02/2024", atualizado.getMesAno());
        Assertions.assertEquals(false, atualizado.getAberto());

        Assertions.assertEquals(5, atualizado.getEncaminhamento().getEncSaude());
        Assertions.assertEquals(10, atualizado.getOutrosNumeros().getAlimentacao());
        Assertions.assertEquals(20, atualizado.getAcoesRealizadas().getTotalAtividadesGrupoVirtual());

        // Verificar se salvou (se tiver save no método)
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(RelatorioEntity.class));
    }
}