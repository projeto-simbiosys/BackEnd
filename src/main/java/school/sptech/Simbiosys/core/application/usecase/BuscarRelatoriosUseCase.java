package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;


@Service
public class BuscarRelatoriosUseCase {

    private final RelatorioGateway gateway;

    public BuscarRelatoriosUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public Page<RelatorioEntity> execute(Pageable pageable) {
        return gateway.findAll(pageable);
    }
}
