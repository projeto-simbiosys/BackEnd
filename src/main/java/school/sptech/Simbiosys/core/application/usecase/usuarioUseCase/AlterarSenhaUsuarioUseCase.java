package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.Optional;

@Service
public class AlterarSenhaUsuarioUseCase {

    private final UsuarioGateway gateway;

    public AlterarSenhaUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public boolean execute(String email, String novaSenha) {
        if (novaSenha == null || novaSenha.length() < 6) {
            return false;
        }

        Optional<UsuarioEntity> usuarioOpt = gateway.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return false;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(novaSenha);

        UsuarioEntity usuario = usuarioOpt.get();
        usuario.setSenha(encryptedPassword);
        gateway.save(usuario);
        return true;
    }
}
