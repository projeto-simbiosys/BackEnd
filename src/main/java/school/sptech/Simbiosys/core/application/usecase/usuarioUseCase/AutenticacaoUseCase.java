package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.core.dto.UsuarioDetalhesDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.Optional;

@Service
public class AutenticacaoUseCase implements UserDetailsService {

    private final UsuarioGateway gateway;

    public AutenticacaoUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioEntity> usuarioOpt = gateway.findByEmail(username);

        if (usuarioOpt.isEmpty()) {

            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new UsuarioDetalhesDto(usuarioOpt.get());
    }
}
