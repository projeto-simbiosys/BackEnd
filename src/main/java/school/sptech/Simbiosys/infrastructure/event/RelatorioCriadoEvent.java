package school.sptech.Simbiosys.infrastructure.event;

import org.springframework.context.ApplicationEvent;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

public class RelatorioCriadoEvent extends ApplicationEvent {

    private final RelatorioEntity relatorio;


    public RelatorioCriadoEvent(Object source, RelatorioEntity relatorio) {
        super(source);
        this.relatorio = relatorio;
    }

    public RelatorioEntity getRelatorio() {
        return relatorio;
    }
}
