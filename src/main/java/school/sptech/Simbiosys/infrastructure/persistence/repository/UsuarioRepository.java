package school.sptech.Simbiosys.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmailIgnoreCaseContaining(String email);

    List<UsuarioEntity> findByNomeContainingIgnoreCase(String nome);

}
