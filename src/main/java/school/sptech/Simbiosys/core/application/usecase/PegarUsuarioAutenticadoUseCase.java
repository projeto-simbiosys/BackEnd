package school.sptech.Simbiosys.core.application.usecase;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.dto.UsuarioDetalhesDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;
import school.sptech.Simbiosys.infrastructure.persistence.repository.UsuarioRepository;

@Service
public class PegarUsuarioAutenticadoUseCase {

    private final UsuarioRepository usuarioRepository;

    public PegarUsuarioAutenticadoUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity execute(Authentication authentication) {
        UsuarioDetalhesDto usuarioDetalhes = (UsuarioDetalhesDto) authentication.getPrincipal();

        return usuarioRepository.findByEmail(usuarioDetalhes.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
