package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.core.dto.UsuarioMapper;
import school.sptech.Simbiosys.core.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.Collections;
import java.util.List;

@Service
public class BuscarPorNomeUsuarioUseCase {
    private final UsuarioGateway gateway;

    public BuscarPorNomeUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public List<UsuarioResponseDto> execute(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<UsuarioEntity> usuariosEncontrados = gateway.findByNomeContainingIgnoreCase(nome);
        return usuariosEncontrados.stream().map(UsuarioMapper::of).toList();
    }
}
