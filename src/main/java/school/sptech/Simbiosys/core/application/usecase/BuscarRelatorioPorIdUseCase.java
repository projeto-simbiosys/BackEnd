package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.core.application.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

@Service
public class BuscarRelatorioPorIdUseCase {

    private final RelatorioGateway gateway;

    public BuscarRelatorioPorIdUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public RelatorioEntity execute(Integer id) {
        return gateway.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório de id: %d não encontrado".formatted(id)));
    }
}