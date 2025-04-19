package school.sptech.Simbiosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.Simbiosys.model.Relatorio;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Integer> {


    boolean existsByMesAno(String mesAno);

    Relatorio findByMesAno(String mesAno);
}
