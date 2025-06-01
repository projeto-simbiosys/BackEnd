package school.sptech.Simbiosys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.dto.UsuarioDetalhesDto;
import school.sptech.Simbiosys.event.RelatorioCriadoEvent;
import school.sptech.Simbiosys.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.exception.RelatorioNaoEncontradoException;
import school.sptech.Simbiosys.model.*;
import school.sptech.Simbiosys.repository.RelatorioRepository;
import school.sptech.Simbiosys.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private final RelatorioRepository repository;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher publisher;

    public RelatorioService(RelatorioRepository repository, ApplicationEventPublisher publisher, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.publisher = publisher;
        this.usuarioRepository = usuarioRepository;
    }

    public Relatorio cadastrar(Relatorio relatorio, Authentication authentication){
        if(repository.existsByMesAno(relatorio.getMesAno())){
            throw new EntidadeJaExistente("Relatório com nome %s já cadastrado".formatted(relatorio.getMesAno()));
        }

        relatorio.setUsuario(pegarUsuarioAutenticado(authentication));
        Relatorio salvo = repository.save(relatorio);
        publisher.publishEvent(new RelatorioCriadoEvent(this, salvo));

        return salvo;
    }

    public Relatorio buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório de id: %d não encontrado".formatted(id)));
    }

    public void deletar(Integer id){
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    "Produto de id: %d não encontrado".formatted(id)
            );
        }
        repository.deleteById(id);
    }

    public Relatorio atualizar(Integer id, Relatorio input, Authentication authentication) {
        Relatorio relatorio = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado com id: " + id));

        if (input.getMesAno() != null) {
            relatorio.setMesAno(input.getMesAno());
        }

        if (input.getAberto() != null) {
            relatorio.setAberto(input.getAberto());
        }

        if (input.getEncaminhamento() != null) {
            Encaminhamento enc = relatorio.getEncaminhamento();
            if (enc == null) enc = new Encaminhamento();
            Encaminhamento inEnc = input.getEncaminhamento();

            if (inEnc.getEncBeneficioPrestacaoContinuada() != null) enc.setEncBeneficioPrestacaoContinuada(inEnc.getEncBeneficioPrestacaoContinuada());
            if (inEnc.getEncAposentadoria() != null) enc.setEncAposentadoria(inEnc.getEncAposentadoria());
            if (inEnc.getEncAssistenciaSocial() != null) enc.setEncAssistenciaSocial(inEnc.getEncAssistenciaSocial());
            if (inEnc.getEncCursosProfissionalizantesForaOrganizacao() != null) enc.setEncCursosProfissionalizantesForaOrganizacao(inEnc.getEncCursosProfissionalizantesForaOrganizacao());
            if (inEnc.getEncCursosProfissionalizantesDentroOrganizacao() != null) enc.setEncCursosProfissionalizantesDentroOrganizacao(inEnc.getEncCursosProfissionalizantesDentroOrganizacao());
            if (inEnc.getEncEducacaoNaoFormal() != null) enc.setEncEducacaoNaoFormal(inEnc.getEncEducacaoNaoFormal());
            if (inEnc.getEncEducacaoFormal() != null) enc.setEncEducacaoFormal(inEnc.getEncEducacaoFormal());
            if (inEnc.getEncDocumentos() != null) enc.setEncDocumentos(inEnc.getEncDocumentos());
            if (inEnc.getEncTrabalho() != null) enc.setEncTrabalho(inEnc.getEncTrabalho());
            if (inEnc.getEncGeracaoRenda() != null) enc.setEncGeracaoRenda(inEnc.getEncGeracaoRenda());
            if (inEnc.getEncSaude() != null) enc.setEncSaude(inEnc.getEncSaude());
            if (inEnc.getEncTratamentoDrogas() != null) enc.setEncTratamentoDrogas(inEnc.getEncTratamentoDrogas());
            if (inEnc.getEncProgramasTransferenciaRenda() != null) enc.setEncProgramasTransferenciaRenda(inEnc.getEncProgramasTransferenciaRenda());
            if (inEnc.getEncPoliticasPublicas() != null) enc.setEncPoliticasPublicas(inEnc.getEncPoliticasPublicas());

            relatorio.setEncaminhamento(enc);
        }

        if (input.getOutrosNumeros() != null) {
            OutrosNumeros outros = relatorio.getOutrosNumeros();
            if (outros == null) outros = new OutrosNumeros();
            OutrosNumeros inOutros = input.getOutrosNumeros();

            if (inOutros.getAlimentacao() != null) outros.setAlimentacao(inOutros.getAlimentacao());
            if (inOutros.getNumeroDePessoasPresencial() != null) outros.setNumeroDePessoasPresencial(inOutros.getNumeroDePessoasPresencial());
            if (inOutros.getCestasBasicasDoadas() != null) outros.setCestasBasicasDoadas(inOutros.getCestasBasicasDoadas());
            if (inOutros.getKitsHigieneDoados() != null) outros.setKitsHigieneDoados(inOutros.getKitsHigieneDoados());
            if (inOutros.getTotalParticipantesAtividadeDistancia() != null) outros.setTotalParticipantesAtividadeDistancia(inOutros.getTotalParticipantesAtividadeDistancia());
            if (inOutros.getTotalParticipantesAtividadePresencial() != null) outros.setTotalParticipantesAtividadePresencial(inOutros.getTotalParticipantesAtividadePresencial());

            relatorio.setOutrosNumeros(outros);
        }

        if (input.getAcoesRealizadas() != null) {
            AcoesRealizadas acoes = relatorio.getAcoesRealizadas();
            if (acoes == null) acoes = new AcoesRealizadas();
            AcoesRealizadas inAcoes = input.getAcoesRealizadas();

            if (inAcoes.getTotalAtividadesGrupoVirtual() != null) acoes.setTotalAtividadesGrupoVirtual(inAcoes.getTotalAtividadesGrupoVirtual());
            if (inAcoes.getTotalAtividadesCulturaisExternas() != null) acoes.setTotalAtividadesCulturaisExternas(inAcoes.getTotalAtividadesCulturaisExternas());
            if (inAcoes.getTotalAtividadesCulturaisVirtuais() != null) acoes.setTotalAtividadesCulturaisVirtuais(inAcoes.getTotalAtividadesCulturaisVirtuais());
            if (inAcoes.getTotalPalestrasPresenciais() != null) acoes.setTotalPalestrasPresenciais(inAcoes.getTotalPalestrasPresenciais());
            if (inAcoes.getTotalPalestrasVirtuais() != null) acoes.setTotalPalestrasVirtuais(inAcoes.getTotalPalestrasVirtuais());
            if (inAcoes.getTotalVisitasFamiliaresPresenciais() != null) acoes.setTotalVisitasFamiliaresPresenciais(inAcoes.getTotalVisitasFamiliaresPresenciais());
            if (inAcoes.getTotalVisitasFamiliaresVirtuais() != null) acoes.setTotalVisitasFamiliaresVirtuais(inAcoes.getTotalVisitasFamiliaresVirtuais());
            if (inAcoes.getTotalVisitasMonitoradasPresenciais() != null) acoes.setTotalVisitasMonitoradasPresenciais(inAcoes.getTotalVisitasMonitoradasPresenciais());
            if (inAcoes.getTotalVisitasMonitoradasVirtuais() != null) acoes.setTotalVisitasMonitoradasVirtuais(inAcoes.getTotalVisitasMonitoradasVirtuais());
            if (inAcoes.getTotalCursosMinistradosPresenciais() != null) acoes.setTotalCursosMinistradosPresenciais(inAcoes.getTotalCursosMinistradosPresenciais());
            if (inAcoes.getTotalCursosMinistradosVirtuais() != null) acoes.setTotalCursosMinistradosVirtuais(inAcoes.getTotalCursosMinistradosVirtuais());
            if (inAcoes.getTotalPessoasCursosCapacitacaoPresenciais() != null) acoes.setTotalPessoasCursosCapacitacaoPresenciais(inAcoes.getTotalPessoasCursosCapacitacaoPresenciais());
            if (inAcoes.getTotalPessoasCursosCapacitacaoVirtuais() != null) acoes.setTotalPessoasCursosCapacitacaoVirtuais(inAcoes.getTotalPessoasCursosCapacitacaoVirtuais());
            if (inAcoes.getTotalPessoasCursosProfissionalizantesPresenciais() != null) acoes.setTotalPessoasCursosProfissionalizantesPresenciais(inAcoes.getTotalPessoasCursosProfissionalizantesPresenciais());
            if (inAcoes.getTotalPessoasCursosProfissionalizantesVirtuais() != null) acoes.setTotalPessoasCursosProfissionalizantesVirtuais(inAcoes.getTotalPessoasCursosProfissionalizantesVirtuais());

            relatorio.setAcoesRealizadas(acoes);
        }

        input.setUsuario(pegarUsuarioAutenticado(authentication));
        relatorio.setUsuario(pegarUsuarioAutenticado(authentication));
        relatorio.setDataAtualizacao(LocalDateTime.now());

        return repository.save(relatorio);
    }

    public Relatorio buscarPorMesAno(String mesAno) {
        if (!repository.existsByMesAno(mesAno)) {
            throw new EntidadeNaoEncontradaException("Relatório com mes/ano: %s não encontrado".formatted(mesAno));
        }
        return repository.findByMesAno(mesAno);
    }

    public Relatorio somarRelatoriosPorAno(String ano) {
        List<Relatorio> relatorios = repository.findByAno(ano);

        if (relatorios.isEmpty()) {
            return null;
        }

        Relatorio relatorioSomado = new Relatorio();
        relatorioSomado.setMesAno("Ano " + ano);
        relatorioSomado.setDataAtualizacao(LocalDateTime.now());
        relatorioSomado.setUsuario(null);
        Encaminhamento encaminhamentoTotal = new Encaminhamento();
        OutrosNumeros outrosNumerosTotal = new OutrosNumeros();
        AcoesRealizadas acoesRealizadasTotal = new AcoesRealizadas();

        for (Relatorio r : relatorios) {
            Encaminhamento e = r.getEncaminhamento();
            if (e != null) {
                encaminhamentoTotal.somar(e);
            }

            OutrosNumeros o = r.getOutrosNumeros();
            if (o != null) {
                outrosNumerosTotal.somar(o);
            }

            AcoesRealizadas a = r.getAcoesRealizadas();
            if (a != null) {
                acoesRealizadasTotal.somar(a);
            }
        }

        relatorioSomado.setEncaminhamento(encaminhamentoTotal);
        relatorioSomado.setOutrosNumeros(outrosNumerosTotal);
        relatorioSomado.setAcoesRealizadas(acoesRealizadasTotal);

        return relatorioSomado;
    }

    public Relatorio somarRelatoriosPorPeriodo(String de, String para) {
        List<Relatorio> relatorios = repository.findByPeriodo(de, para);

        System.out.println("Relatórios encontrados: " + relatorios.size());

        if (relatorios.isEmpty()) {
            throw new RelatorioNaoEncontradoException("Nenhum relatório encontrado para o período de " + de + " até " + para);
        }

        Relatorio relatorioSomado = new Relatorio();
        relatorioSomado.setMesAno(de + " até " + para);

        for (Relatorio r : relatorios) {
            relatorioSomado.somar(r);
        }

        return relatorioSomado;
    }

    public List<Relatorio> buscarRelatoriosPorAno(String ano) {
        return repository.findByAno(ano);
    }

    public List<Relatorio> buscarRelatoriosPorPeriodo(String de, String para) {
        return repository.findByPeriodo(de, para);
    }

    private Usuario pegarUsuarioAutenticado(Authentication authentication){
        UsuarioDetalhesDto usuarioDetalhes = (UsuarioDetalhesDto) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByEmail(usuarioDetalhes.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuario;
    }
}

