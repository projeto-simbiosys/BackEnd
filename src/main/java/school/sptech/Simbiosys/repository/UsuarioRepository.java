package school.sptech.Simbiosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.Simbiosys.model.Usuario;

import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

    boolean existsByEmailIgnoreCaseContaining(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
