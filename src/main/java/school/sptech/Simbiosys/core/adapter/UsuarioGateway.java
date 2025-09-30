package school.sptech.Simbiosys.core.adapter;

import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioGateway {

    UsuarioEntity save(UsuarioEntity usuario);
    List<UsuarioEntity> findAll();
    Optional<UsuarioEntity> findByEmail(String email);
    boolean existsByEmailIgnoreCaseContaining(String email);
    List<UsuarioEntity> findByNomeContainingIgnoreCase(String nome);
    Optional<UsuarioEntity> findById(Integer id);
    boolean existsById(Integer id);
    void deleteById(Integer id);
}
