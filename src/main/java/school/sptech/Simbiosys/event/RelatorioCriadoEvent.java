package school.sptech.Simbiosys.event;

import org.springframework.context.ApplicationEvent;
import school.sptech.Simbiosys.model.Relatorio;

public class RelatorioCriadoEvent extends ApplicationEvent {

    private final Relatorio relatorio;


    public RelatorioCriadoEvent(Object source, Relatorio relatorio) {
        super(source);
        this.relatorio = relatorio;
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }
}
