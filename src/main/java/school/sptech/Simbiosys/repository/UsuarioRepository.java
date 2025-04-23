package school.sptech.Simbiosys.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.Simbiosys.model.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

    boolean existsByEmailIgnoreCaseContaining(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

}
