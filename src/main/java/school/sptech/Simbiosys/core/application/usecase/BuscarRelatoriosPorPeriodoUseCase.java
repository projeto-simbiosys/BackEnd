package school.sptech.Simbiosys.core.application.usecase;

import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

import java.util.List;

public class BuscarRelatoriosPorPeriodoUseCase {

    private final RelatorioGateway gateway;

    public BuscarRelatoriosPorPeriodoUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public List<RelatorioEntity> execute(String de, String para) {
        return gateway.findByPeriodo(de, para);
    }
}
