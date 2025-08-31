package school.sptech.Simbiosys.infrastructure.persistence.mapper;

import school.sptech.Simbiosys.core.domain.Encaminhamento;
import school.sptech.Simbiosys.infrastructure.persistence.entity.EncaminhamentoEntity;

public class EncaminhamentoEntityMapper {

    public static EncaminhamentoEntity ofDomain(Encaminhamento domain) {
        if (domain == null) {
            return null;
        }

        EncaminhamentoEntity entity = new EncaminhamentoEntity();
        entity.setEncBeneficioPrestacaoContinuada(domain.getEncBeneficioPrestacaoContinuada());
        entity.setEncAposentadoria(domain.getEncAposentadoria());
        entity.setEncAssistenciaSocial(domain.getEncAssistenciaSocial());
        entity.setEncCursosProfissionalizantesForaOrganizacao(domain.getEncCursosProfissionalizantesForaOrganizacao());
        entity.setEncCursosProfissionalizantesDentroOrganizacao(domain.getEncCursosProfissionalizantesDentroOrganizacao());
        entity.setEncEducacaoNaoFormal(domain.getEncEducacaoNaoFormal());
        entity.setEncEducacaoFormal(domain.getEncEducacaoFormal());
        entity.setEncDocumentos(domain.getEncDocumentos());
        entity.setEncTrabalho(domain.getEncTrabalho());
        entity.setEncGeracaoRenda(domain.getEncGeracaoRenda());
        entity.setEncSaude(domain.getEncSaude());
        entity.setEncTratamentoDrogas(domain.getEncTratamentoDrogas());
        entity.setEncProgramasTransferenciaRenda(domain.getEncProgramasTransferenciaRenda());
        entity.setEncPoliticasPublicas(domain.getEncPoliticasPublicas());
        return entity;
    }

    public static Encaminhamento ofEntity(EncaminhamentoEntity entity) {
        if (entity == null) {
            return null;
        }

        Encaminhamento domain = new Encaminhamento();
        domain.setEncBeneficioPrestacaoContinuada(entity.getEncBeneficioPrestacaoContinuada());
        domain.setEncAposentadoria(entity.getEncAposentadoria());
        domain.setEncAssistenciaSocial(entity.getEncAssistenciaSocial());
        domain.setEncCursosProfissionalizantesForaOrganizacao(entity.getEncCursosProfissionalizantesForaOrganizacao());
        domain.setEncCursosProfissionalizantesDentroOrganizacao(entity.getEncCursosProfissionalizantesDentroOrganizacao());
        domain.setEncEducacaoNaoFormal(entity.getEncEducacaoNaoFormal());
        domain.setEncEducacaoFormal(entity.getEncEducacaoFormal());
        domain.setEncDocumentos(entity.getEncDocumentos());
        domain.setEncTrabalho(entity.getEncTrabalho());
        domain.setEncGeracaoRenda(entity.getEncGeracaoRenda());
        domain.setEncSaude(entity.getEncSaude());
        domain.setEncTratamentoDrogas(entity.getEncTratamentoDrogas());
        domain.setEncProgramasTransferenciaRenda(entity.getEncProgramasTransferenciaRenda());
        domain.setEncPoliticasPublicas(entity.getEncPoliticasPublicas());
        return domain;
    }
}
