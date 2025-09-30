package school.sptech.Simbiosys.core.application.usecase.usuarioUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.Simbiosys.core.adapter.UsuarioGateway;
import school.sptech.Simbiosys.core.application.exception.DadosInvalidosException;
import school.sptech.Simbiosys.core.dto.UsuarioMapper;
import school.sptech.Simbiosys.core.dto.UsuarioRequestDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class CadastrarUsuarioUseCase {

    private List<UsuarioEntity> usuarios = new ArrayList<>();

    private final UsuarioGateway gateway;

    public CadastrarUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public UsuarioEntity execute(UsuarioRequestDto dto) {

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new DadosInvalidosException("O nome é obrigatório.");
        }

        if (dto.getEmail() == null || !dto.getEmail().contains("@") || !dto.getEmail().contains(".")) {
            throw new DadosInvalidosException("Email inválido.");
        }

        if (dto.getSenha() == null || dto.getSenha().length() < 6) {
            throw new DadosInvalidosException("A senha deve ter pelo menos 6 caracteres.");
        }

        if (gateway.existsByEmailIgnoreCaseContaining(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário cadastrado com este email.");
        }

        if (!dto.getToken().equals("#ACESSO-CEFOPEA")) {
            throw new DadosInvalidosException("Token para cadastro inválido.");
        }

        UsuarioEntity usuarioSalvo = gateway.save(UsuarioMapper.of(dto));
        usuarios.add(usuarioSalvo);

        return usuarioSalvo;
    }
}
