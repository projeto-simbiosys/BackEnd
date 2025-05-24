package school.sptech.Simbiosys.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import school.sptech.Simbiosys.model.Usuario;

public class UsuarioMapper {
    public static Usuario of(UsuarioRequestDto usuarioCriacaoDto) {
        Usuario usuario = new Usuario();

        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioCriacaoDto.getSenha());

        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setSobrenome(usuarioCriacaoDto.getSobrenome());
        usuario.setCargo(usuarioCriacaoDto.getCargo());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setSenha(encryptedPassword);
        usuario.setToken(usuarioCriacaoDto.getToken());

        return usuario;
    }

    public static Usuario of(UsuarioLoginDto usuarioLoginDto) {
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioLoginDto.getEmail());
        usuario.setSenha(usuarioLoginDto.getSenha());

        return usuario;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuario.getId());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

    public static UsuarioResponseDto of(Usuario usuario) {
        UsuarioResponseDto usuarioListarDto = new UsuarioResponseDto();

        usuarioListarDto.setId(usuario.getId());
        usuarioListarDto.setNome(usuario.getNome());
        usuarioListarDto.setSobrenome(usuario.getSobrenome());
        usuarioListarDto.setCargo(usuario.getCargo());
        usuarioListarDto.setEmail(usuario.getEmail());

        return usuarioListarDto;
    }

}