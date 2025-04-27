package school.sptech.Simbiosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.Simbiosys.model.Relatorio;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Integer> {


    boolean existsByMesAno(String mesAno);

    Relatorio findByMesAno(String mesAno);

    @Query("SELECT r FROM Relatorio r WHERE r.mesAno LIKE %:ano%")
    List<Relatorio> findByAno(@Param("ano") String ano);

    @Query("SELECT r FROM Relatorio r WHERE r.mesAno >= :de AND r.mesAno <= :para")
    List<Relatorio> findByPeriodo(@Param("de") String de, @Param("para") String para);
}
