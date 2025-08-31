package school.sptech.Simbiosys.infrastructure.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import school.sptech.Simbiosys.infrastructure.event.RelatorioCriadoEvent;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;

@Component
public class RelatorioListener {

    @EventListener
    public void notificarNovoRelatorio(RelatorioCriadoEvent event) {
        RelatorioEntity relatorio = event.getRelatorio();

        String mensagem = String.format("📢 Novo relatório disponível: %s (atualizado em %s)",
                relatorio.getMesAno(),
                relatorio.getDataAtualizacao());
        System.out.println(mensagem);
    }
}
