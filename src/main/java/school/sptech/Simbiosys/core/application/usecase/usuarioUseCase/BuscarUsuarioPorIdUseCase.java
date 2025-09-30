package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.core.dto.UsuarioMapper;
import school.sptech.Simbiosys.core.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

@Service
public class BuscarUsuarioPorIdUseCase {

    private final UsuarioGateway gateway;

    public BuscarUsuarioPorIdUseCase(UsuarioGateway gateway, List<UsuarioEntity> usuarios) {
        this.gateway = gateway;
    }

    public Optional<UsuarioResponseDto> execute(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return gateway.findById(id)
                .map(UsuarioMapper::of);
    }
}
