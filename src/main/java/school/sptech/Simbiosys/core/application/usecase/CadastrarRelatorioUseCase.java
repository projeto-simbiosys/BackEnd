package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import school.sptech.Simbiosys.core.adapter.RelatorioGateway;
import school.sptech.Simbiosys.core.application.exception.EntidadeJaExistente;
import school.sptech.Simbiosys.infrastructure.event.RelatorioCriadoEvent;
import school.sptech.Simbiosys.infrastructure.persistence.entity.RelatorioEntity;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;
import school.sptech.Simbiosys.infrastructure.persistence.repository.RelatorioRepository;

public class CadastrarRelatorioUseCase {

    private final RelatorioGateway gateway;
    private final ApplicationEventPublisher publisher;
    private final PegarUsuarioAutenticadoUseCase pegarUsuarioAutenticadoUseCase;

    public CadastrarRelatorioUseCase(RelatorioGateway gateway, ApplicationEventPublisher publisher, PegarUsuarioAutenticadoUseCase pegarUsuarioAutenticadoUseCase) {
        this.gateway = gateway;
        this.publisher = publisher;
        this.pegarUsuarioAutenticadoUseCase = pegarUsuarioAutenticadoUseCase;
    }

    public RelatorioEntity execute(RelatorioEntity relatorio, Authentication authentication) {
        if (gateway.existsByMesAno(relatorio.getMesAno())) {
            throw new EntidadeJaExistente("Relatório com nome %s já cadastrado".formatted(relatorio.getMesAno()));
        }

        UsuarioEntity usuario = pegarUsuarioAutenticadoUseCase.execute(authentication);
        relatorio.setUsuario(usuario);
        RelatorioEntity salvo = gateway.save(relatorio);
        publisher.publishEvent(new RelatorioCriadoEvent(this, salvo));

        return salvo;
    }
}
