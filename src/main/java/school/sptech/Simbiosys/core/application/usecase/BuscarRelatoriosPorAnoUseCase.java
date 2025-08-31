package school.sptech.Simbiosys.core.application.usecase;

import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

import java.util.List;

public class BuscarRelatoriosPorAnoUseCase {

    private final RelatorioGateway gateway;

    public BuscarRelatoriosPorAnoUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public List<RelatorioEntity> execute(String ano) {
        return gateway.findByAno(ano);
    }
}