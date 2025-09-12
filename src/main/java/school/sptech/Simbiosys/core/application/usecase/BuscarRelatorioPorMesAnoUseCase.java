package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.core.application.exception.EntidadeNaoEncontradaException;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

@Service
public class BuscarRelatorioPorMesAnoUseCase {

    private final RelatorioGateway gateway;

    public BuscarRelatorioPorMesAnoUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public RelatorioEntity execute(String mesAno) {
        if (!gateway.existsByMesAno(mesAno)) {
            throw new EntidadeNaoEncontradaException("Relatório com mes/ano: %s não encontrado".formatted(mesAno));
        }
        return gateway.findByMesAno(mesAno);
    }
}