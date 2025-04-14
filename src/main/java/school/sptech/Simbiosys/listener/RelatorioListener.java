package school.sptech.Simbiosys.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import school.sptech.Simbiosys.event.RelatorioCriadoEvent;
import school.sptech.Simbiosys.model.Relatorio;

@Component
public class RelatorioListener {

    @EventListener
    public void notificarNovoRelatorio(RelatorioCriadoEvent event) {
        Relatorio relatorio = event.getRelatorio();

        String mensagem = String.format("📢 Novo relatório disponível: %s (atualizado em %s)",
                relatorio.getMesAno(),
                relatorio.getDataAtualizacao());
        System.out.println(mensagem);
    }
}
