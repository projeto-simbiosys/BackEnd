package school.sptech.Simbiosys.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<RelatorioEntity, Integer> {


    boolean existsByMesAno(String mesAno);

    RelatorioEntity findByMesAno(String mesAno);

    @Query("SELECT r FROM RelatorioEntity r WHERE r.mesAno LIKE %:ano%")
    Page<RelatorioEntity> findByAno(@Param("ano") String ano, Pageable pageable);

    @Query("SELECT r FROM RelatorioEntity r WHERE r.mesAno >= :de AND r.mesAno <= :para")
    List<RelatorioEntity> findByPeriodo(@Param("de") String de, @Param("para") String para);
}
