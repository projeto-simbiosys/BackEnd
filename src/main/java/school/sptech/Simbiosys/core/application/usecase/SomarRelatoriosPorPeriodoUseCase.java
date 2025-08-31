package school.sptech.Simbiosys.core.application.usecase;

import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.core.application.exception.RelatorioNaoEncontradoException;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

import java.util.List;

public class SomarRelatoriosPorPeriodoUseCase {

    private final RelatorioGateway gateway;

    public SomarRelatoriosPorPeriodoUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public RelatorioEntity execute(String de, String para) {
        List<RelatorioEntity> relatorios = gateway.findByPeriodo(de, para);

        if (relatorios.isEmpty()) {
            throw new RelatorioNaoEncontradoException("Nenhum relatório encontrado para o período de " + de + " até " + para);
        }

        RelatorioEntity relatorioSomado = new RelatorioEntity();
        relatorioSomado.setMesAno(de + " até " + para);

        for (RelatorioEntity r : relatorios) {
            relatorioSomado.somar(r);
        }

        return relatorioSomado;
    }
}
