package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SomarRelatoriosPorAnoUseCase {

    private final RelatorioGateway gateway;

    public SomarRelatoriosPorAnoUseCase(RelatorioGateway gateway) {
        this.gateway = gateway;
    }

    public RelatorioEntity execute(String ano) {
        List<RelatorioEntity> relatorios = gateway.findByAno(ano);

        if (relatorios.isEmpty()) {
            return null;
        }

        RelatorioEntity relatorioSomado = new RelatorioEntity();
        relatorioSomado.setMesAno("Ano " + ano);
        relatorioSomado.setDataAtualizacao(LocalDateTime.now());
        relatorioSomado.setUsuario(null);
        EncaminhamentoEntity encaminhamentoTotal = new EncaminhamentoEntity();
        OutrosNumerosEntity outrosNumerosEntityTotal = new OutrosNumerosEntity();
        AcoesRealizadasEntity acoesRealizadasTotal = new AcoesRealizadasEntity();

        for (RelatorioEntity r : relatorios) {
            if (r.getEncaminhamento() != null) {
                encaminhamentoTotal.somar(r.getEncaminhamento());
            }
            if (r.getOutrosNumeros() != null) {
                outrosNumerosEntityTotal.somar(r.getOutrosNumeros());
            }
            if (r.getAcoesRealizadas() != null) {
                acoesRealizadasTotal.somar(r.getAcoesRealizadas());
            }
        }

        relatorioSomado.setEncaminhamento(encaminhamentoTotal);
        relatorioSomado.setOutrosNumeros(outrosNumerosEntityTotal);
        relatorioSomado.setAcoesRealizadas(acoesRealizadasTotal);

        return relatorioSomado;
    }
}
