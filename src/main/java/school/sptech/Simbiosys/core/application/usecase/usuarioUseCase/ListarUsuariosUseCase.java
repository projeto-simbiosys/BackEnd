package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.core.dto.UsuarioMapper;
import school.sptech.Simbiosys.core.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.domain.Pageable;

@Service
public class ListarUsuariosUseCase {


    private final UsuarioGateway gateway;

    public ListarUsuariosUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Page<UsuarioResponseDto> execute(Pageable pageable) {
        Page<UsuarioEntity> usuariosEncontrados = gateway.findAll(pageable);
        return usuariosEncontrados.map(UsuarioMapper::of);
    }
}
