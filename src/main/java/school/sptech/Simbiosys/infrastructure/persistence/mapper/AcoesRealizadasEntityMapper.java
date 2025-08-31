package school.sptech.Simbiosys.infrastructure.persistence.mapper;

import school.sptech.Simbiosys.core.domain.AcoesRealizadas;
import school.sptech.Simbiosys.infrastructure.persistence.entity.AcoesRealizadasEntity;

public class AcoesRealizadasEntityMapper {

    public static AcoesRealizadasEntity ofDomain(AcoesRealizadas domain) {
        if (domain == null) {
            return null;
        }

        AcoesRealizadasEntity entity = new AcoesRealizadasEntity();
        entity.setTotalAtividadesGrupoVirtual(domain.getTotalAtividadesGrupoVirtual());
        entity.setTotalAtividadesCulturaisExternas(domain.getTotalAtividadesCulturaisExternas());
        entity.setTotalAtividadesCulturaisVirtuais(domain.getTotalAtividadesCulturaisVirtuais());
        entity.setTotalPalestrasPresenciais(domain.getTotalPalestrasPresenciais());
        entity.setTotalPalestrasVirtuais(domain.getTotalPalestrasVirtuais());
        entity.setTotalVisitasFamiliaresPresenciais(domain.getTotalVisitasFamiliaresPresenciais());
        entity.setTotalVisitasFamiliaresVirtuais(domain.getTotalVisitasFamiliaresVirtuais());
        entity.setTotalVisitasMonitoradasPresenciais(domain.getTotalVisitasMonitoradasPresenciais());
        entity.setTotalVisitasMonitoradasVirtuais(domain.getTotalVisitasMonitoradasVirtuais());
        entity.setTotalCursosMinistradosPresenciais(domain.getTotalCursosMinistradosPresenciais());
        entity.setTotalCursosMinistradosVirtuais(domain.getTotalCursosMinistradosVirtuais());
        entity.setTotalPessoasCursosCapacitacaoPresenciais(domain.getTotalPessoasCursosCapacitacaoPresenciais());
        entity.setTotalPessoasCursosCapacitacaoVirtuais(domain.getTotalPessoasCursosCapacitacaoVirtuais());
        entity.setTotalPessoasCursosProfissionalizantesPresenciais(domain.getTotalPessoasCursosProfissionalizantesPresenciais());
        entity.setTotalPessoasCursosProfissionalizantesVirtuais(domain.getTotalPessoasCursosProfissionalizantesVirtuais());
        return entity;
    }

    public static AcoesRealizadas ofEntity(AcoesRealizadasEntity entity) {
        if (entity == null) {
            return null;
        }

        AcoesRealizadas domain = new AcoesRealizadas();
        domain.setTotalAtividadesGrupoVirtual(entity.getTotalAtividadesGrupoVirtual());
        domain.setTotalAtividadesCulturaisExternas(entity.getTotalAtividadesCulturaisExternas());
        domain.setTotalAtividadesCulturaisVirtuais(entity.getTotalAtividadesCulturaisVirtuais());
        domain.setTotalPalestrasPresenciais(entity.getTotalPalestrasPresenciais());
        domain.setTotalPalestrasVirtuais(entity.getTotalPalestrasVirtuais());
        domain.setTotalVisitasFamiliaresPresenciais(entity.getTotalVisitasFamiliaresPresenciais());
        domain.setTotalVisitasFamiliaresVirtuais(entity.getTotalVisitasFamiliaresVirtuais());
        domain.setTotalVisitasMonitoradasPresenciais(entity.getTotalVisitasMonitoradasPresenciais());
        domain.setTotalVisitasMonitoradasVirtuais(entity.getTotalVisitasMonitoradasVirtuais());
        domain.setTotalCursosMinistradosPresenciais(entity.getTotalCursosMinistradosPresenciais());
        domain.setTotalCursosMinistradosVirtuais(entity.getTotalCursosMinistradosVirtuais());
        domain.setTotalPessoasCursosCapacitacaoPresenciais(entity.getTotalPessoasCursosCapacitacaoPresenciais());
        domain.setTotalPessoasCursosCapacitacaoVirtuais(entity.getTotalPessoasCursosCapacitacaoVirtuais());
        domain.setTotalPessoasCursosProfissionalizantesPresenciais(entity.getTotalPessoasCursosProfissionalizantesPresenciais());
        domain.setTotalPessoasCursosProfissionalizantesVirtuais(entity.getTotalPessoasCursosProfissionalizantesVirtuais());
        return domain;
    }
}
