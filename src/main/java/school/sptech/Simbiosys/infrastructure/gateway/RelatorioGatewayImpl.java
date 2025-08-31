package school.sptech.Simbiosys.infrastructure.gateway;

import org.springframework.stereotype.Component;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;
import school.sptech.Simbiosys.infrastructure.persistence.repository.RelatorioRepository;

import java.util.List;
import java.util.Optional;

@Component
public class RelatorioGatewayImpl implements RelatorioGateway {

    private final RelatorioRepository repository;

    public RelatorioGatewayImpl(RelatorioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RelatorioEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public RelatorioEntity save(RelatorioEntity relatorio) {
        return repository.save(relatorio);
    }

    @Override
    public boolean existsByMesAno(String mesAno) {
        return repository.existsByMesAno(mesAno);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public RelatorioEntity findByMesAno(String mesAno) {
        return repository.findByMesAno(mesAno);
    }

    @Override
    public List<RelatorioEntity> findByAno(String ano) {
        return repository.findByAno(ano);
    }

    @Override
    public List<RelatorioEntity> findByPeriodo(String de, String para) {
        return repository.findByPeriodo(de, para);
    }
}