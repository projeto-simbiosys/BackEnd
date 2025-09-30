package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;

@Service
public class DeletarUsuarioUseCase {

    private final UsuarioGateway gateway;

    public DeletarUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public boolean execute(Integer id) {
        if (id == null || id <= 0 || !gateway.existsById(id)) {
            return false;
        }
        gateway.deleteById(id);
        return true;
    }
}
