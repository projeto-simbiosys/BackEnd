package school.sptech.Simbiosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.Simbiosys.model.Atividade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {

    Optional<Atividade> findByNome(String nome);

    List<Atividade> findByDataInicioBetween(LocalDate data1, LocalDate data2);
}
