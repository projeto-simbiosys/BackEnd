package school.sptech.Simbiosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.Simbiosys.model.Aluno;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Optional<Aluno> findByRgOrCpf(String rg, String cpf);

    Optional<Aluno> findByRg(String rg);

    Optional<Aluno> findByCpf(String cpf);

    List<Aluno> findByEscolaridade(String escolaridade);
}
