package school.sptech.Simbiosys.infrastructure.persistence.mapper;

import school.sptech.Simbiosys.core.domain.Usuario;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

public class UsuarioEntityMapper {

    public static UsuarioEntity ofDomain(Usuario domain) {
        if (domain == null) {
            return null;
        }

        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setSobrenome(domain.getSobrenome());
        entity.setCargo(domain.getCargo());
        entity.setEmail(domain.getEmail());
        entity.setSenha(domain.getSenha());
        entity.setToken(domain.getToken());
        return entity;
    }

    public static Usuario ofEntity(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        Usuario domain = new Usuario();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        domain.setSobrenome(entity.getSobrenome());
        domain.setCargo(entity.getCargo());
        domain.setEmail(entity.getEmail());
        domain.setSenha(entity.getSenha());
        domain.setToken(entity.getToken());
        return domain;
    }
}