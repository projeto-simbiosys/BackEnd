package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.security.core.Authentication;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.core.application.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.infrastructure.persistence.entity.*;

import java.time.LocalDateTime;

public class AtualizarRelatorioUseCase {

    private final PegarUsuarioAutenticadoUseCase pegarUsuarioAutenticadoUseCase;
    private final RelatorioGateway gateway;

    public AtualizarRelatorioUseCase(PegarUsuarioAutenticadoUseCase pegarUsuarioAutenticadoUseCase, RelatorioGateway gateway) {
        this.pegarUsuarioAutenticadoUseCase = pegarUsuarioAutenticadoUseCase;
        this.gateway = gateway;
    }

    public RelatorioEntity execute(Integer id, RelatorioEntity input, Authentication authentication) {
        RelatorioEntity relatorio = gateway.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado com id: " + id));

        if (input.getMesAno() != null) {
            relatorio.setMesAno(input.getMesAno());
        }

        if (input.getAberto() != null) {
            relatorio.setAberto(input.getAberto());
        }

        if (input.getEncaminhamento() != null) {
            EncaminhamentoEntity enc = relatorio.getEncaminhamento();
            if (enc == null) enc = new EncaminhamentoEntity();
            EncaminhamentoEntity inEnc = input.getEncaminhamento();

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
            OutrosNumerosEntity outros = relatorio.getOutrosNumeros();
            if (outros == null) outros = new OutrosNumerosEntity();
            OutrosNumerosEntity inOutros = input.getOutrosNumeros();

            if (inOutros.getAlimentacao() != null) outros.setAlimentacao(inOutros.getAlimentacao());
            if (inOutros.getNumeroDePessoasPresencial() != null) outros.setNumeroDePessoasPresencial(inOutros.getNumeroDePessoasPresencial());
            if (inOutros.getCestasBasicasDoadas() != null) outros.setCestasBasicasDoadas(inOutros.getCestasBasicasDoadas());
            if (inOutros.getKitsHigieneDoados() != null) outros.setKitsHigieneDoados(inOutros.getKitsHigieneDoados());
            if (inOutros.getTotalParticipantesAtividadeDistancia() != null) outros.setTotalParticipantesAtividadeDistancia(inOutros.getTotalParticipantesAtividadeDistancia());
            if (inOutros.getTotalParticipantesAtividadePresencial() != null) outros.setTotalParticipantesAtividadePresencial(inOutros.getTotalParticipantesAtividadePresencial());

            relatorio.setOutrosNumeros(outros);
        }

        if (input.getAcoesRealizadas() != null) {
            AcoesRealizadasEntity acoes = relatorio.getAcoesRealizadas();
            if (acoes == null) acoes = new AcoesRealizadasEntity();
            AcoesRealizadasEntity inAcoes = input.getAcoesRealizadas();

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

        UsuarioEntity usuario = pegarUsuarioAutenticadoUseCase.execute(authentication);
        relatorio.setUsuario(usuario);
        relatorio.setDataAtualizacao(LocalDateTime.now());

        return gateway.save(relatorio);
    }
}