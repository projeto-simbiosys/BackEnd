package school.sptech.Simbiosys.infrastructure.persistence.mapper;

import school.sptech.Simbiosys.core.domain.OutrosNumeros;
import school.sptech.Simbiosys.infrastructure.persistence.entity.OutrosNumerosEntity;

public class OutrosNumerosEntityMapper {

    public static OutrosNumerosEntity ofDomain(OutrosNumeros domain) {
        if (domain == null) {
            return null;
        }

        OutrosNumerosEntity entity = new OutrosNumerosEntity();
        entity.setAlimentacao(domain.getAlimentacao());
        entity.setNumeroDePessoasPresencial(domain.getNumeroDePessoasPresencial());
        entity.setCestasBasicasDoadas(domain.getCestasBasicasDoadas());
        entity.setKitsHigieneDoados(domain.getKitsHigieneDoados());
        entity.setTotalParticipantesAtividadeDistancia(domain.getTotalParticipantesAtividadeDistancia());
        entity.setTotalParticipantesAtividadePresencial(domain.getTotalParticipantesAtividadePresencial());
        return entity;
    }

    public static OutrosNumeros ofEntity(OutrosNumerosEntity entity) {
        if (entity == null) {
            return null;
        }

        OutrosNumeros domain = new OutrosNumeros();
        domain.setAlimentacao(entity.getAlimentacao());
        domain.setNumeroDePessoasPresencial(entity.getNumeroDePessoasPresencial());
        domain.setCestasBasicasDoadas(entity.getCestasBasicasDoadas());
        domain.setKitsHigieneDoados(entity.getKitsHigieneDoados());
        domain.setTotalParticipantesAtividadeDistancia(entity.getTotalParticipantesAtividadeDistancia());
        domain.setTotalParticipantesAtividadePresencial(entity.getTotalParticipantesAtividadePresencial());
        return domain;
    }
}
