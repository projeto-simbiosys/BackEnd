package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.core.dto.UsuarioMapper;
import school.sptech.Simbiosys.core.dto.UsuarioResponseDto;

import java.util.Optional;

@Service
public class BuscarPorEmailUsuarioUseCase {

    private final UsuarioGateway gateway;

    public BuscarPorEmailUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Optional<UsuarioResponseDto> execute(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return null;
        }
        return gateway.findByEmail(email)
                .map(UsuarioMapper::of);
    }
}
