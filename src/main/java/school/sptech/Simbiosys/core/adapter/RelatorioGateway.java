package school.sptech.Simbiosys.core.adapter;

import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

import java.util.List;
import java.util.Optional;


public interface RelatorioGateway {
    Optional<RelatorioEntity> findById(Integer id);
    RelatorioEntity save(RelatorioEntity relatorio);
    boolean existsByMesAno(String mesAno);
    boolean existsById(Integer id);
    void deleteById(Integer id);
    RelatorioEntity findByMesAno(String mesAno);
    List<RelatorioEntity> findByAno(String ano);
    List<RelatorioEntity> findByPeriodo(String de, String para);
}