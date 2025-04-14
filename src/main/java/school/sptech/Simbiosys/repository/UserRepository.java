package school.sptech.Simbiosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.Simbiosys.model.User;

public interface UserRepository extends JpaRepository<User, String> {

   UserDetails findByLogin(String login);
}
