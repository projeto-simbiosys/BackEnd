package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.core.dto.UsuarioRequestDto;
import school.sptech.Simbiosys.core.dto.UsuarioResponseDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.Optional;

@Service
public class AtualizarUsuarioUseCase {

    private final UsuarioGateway gateway;

    public AtualizarUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public Optional<UsuarioResponseDto> execute(Integer id, UsuarioRequestDto dto) {
        if (id == null || id <= 0 || dto == null) return Optional.empty();

        Optional<UsuarioEntity> usuarioOpt = gateway.findById(id);
        if (usuarioOpt.isEmpty()) return Optional.empty();

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()
                || dto.getEmail() == null || !dto.getEmail().contains("@")
                || dto.getSenha() == null || dto.getSenha().length() < 6) {
            return Optional.empty();
        }
        UsuarioEntity usuario = usuarioOpt.get();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getSenha());

        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setCargo(dto.getCargo());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(encryptedPassword);

        UsuarioEntity salvo = gateway.save(usuario);
        return Optional.of(new UsuarioResponseDto(salvo));
    }
}
