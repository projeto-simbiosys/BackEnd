package school.sptech.Simbiosys.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sptech.Simbiosys.core.dto.UsuarioDetalhesDto;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;
import school.sptech.Simbiosys.infrastructure.persistence.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()) {

            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new UsuarioDetalhesDto(usuarioOpt.get());
    }
}