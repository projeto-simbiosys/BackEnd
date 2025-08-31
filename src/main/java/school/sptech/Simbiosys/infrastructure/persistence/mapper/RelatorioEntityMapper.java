package school.sptech.Simbiosys.infrastructure.persistence.mapper;

import school.sptech.Simbiosys.core.domain.Relatorio;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

public class RelatorioEntityMapper {

    public static RelatorioEntity ofDomain(Relatorio domain) {
        if (domain == null) {
            return null;
        }

        RelatorioEntity entity = new RelatorioEntity();
        entity.setId(domain.getId());
        entity.setMesAno(domain.getMesAno());
        entity.setDataAtualizacao(domain.getDataAtualizacao());
        entity.setAberto(domain.getAberto());
        entity.setEncaminhamento(EncaminhamentoEntityMapper.ofDomain(domain.getEncaminhamento()));
        entity.setOutrosNumeros(OutrosNumerosEntityMapper.ofDomain(domain.getOutrosNumeros()));
        entity.setAcoesRealizadas(AcoesRealizadasEntityMapper.ofDomain(domain.getAcoesRealizadas()));
        entity.setUsuario(UsuarioEntityMapper.ofDomain(domain.getUsuario()));
        return entity;
    }

    public static Relatorio ofEntity(RelatorioEntity entity) {
        if (entity == null) {
            return null;
        }

        Relatorio domain = new Relatorio();
        domain.setId(entity.getId());
        domain.setMesAno(entity.getMesAno());
        domain.setDataAtualizacao(entity.getDataAtualizacao());
        domain.setAberto(entity.getAberto());
        domain.setEncaminhamento(EncaminhamentoEntityMapper.ofEntity(entity.getEncaminhamento()));
        domain.setOutrosNumeros(OutrosNumerosEntityMapper.ofEntity(entity.getOutrosNumeros()));
        domain.setAcoesRealizadas(AcoesRealizadasEntityMapper.ofEntity(entity.getAcoesRealizadas()));
        domain.setUsuario(UsuarioEntityMapper.ofEntity(entity.getUsuario()));
        return domain;
    }
}
