package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

import java.util.List;

@Service
public class BuscarRelatoriosPorAnoUseCase {

    private final RelatorioGateway gateway;

    public BuscarRelatoriosPorAnoUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public List<RelatorioEntity> execute(String ano) {
        return gateway.findByAno(ano);
    }
}