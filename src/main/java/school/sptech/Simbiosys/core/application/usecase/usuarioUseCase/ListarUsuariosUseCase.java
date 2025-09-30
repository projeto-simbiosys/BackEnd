package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.core.dto.UsuarioMapper;
import school.sptech.Simbiosys.core.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListarUsuariosUseCase {


    private final UsuarioGateway gateway;

    public ListarUsuariosUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public List<UsuarioResponseDto> execute() {
        List<UsuarioEntity> usuariosEncontrados = gateway.findAll();
        return usuariosEncontrados.stream().map(UsuarioMapper::of).toList();
    }
}
