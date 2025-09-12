package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

import java.util.List;

@Service
public class BuscarRelatoriosPorPeriodoUseCase {

    private final RelatorioGateway gateway;

    public BuscarRelatoriosPorPeriodoUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public List<RelatorioEntity> execute(String de, String para) {
        return gateway.findByPeriodo(de, para);
    }
}
