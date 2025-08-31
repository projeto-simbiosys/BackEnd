package school.sptech.Simbiosys.core.application.usecase;

import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.core.application.exception.EntidadeNaoEncontradaException;


public class DeletarRelatorioUseCase {

    private final RelatorioGateway gateway;

    public DeletarRelatorioUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public void execute(Integer id) {
        if (!gateway.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Relatório de id: %d não encontrado".formatted(id));
        }
        gateway.deleteById(id);
    }
}
