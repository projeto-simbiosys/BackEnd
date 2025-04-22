package school.sptech.Simbiosys.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import school.sptech.Simbiosys.config.TokenService;
import school.sptech.Simbiosys.controller.dto.AuthenticationDto;
import school.sptech.Simbiosys.controller.dto.LoginResponseDto;
import school.sptech.Simbiosys.model.Usuario;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "${cors.allowed.origin}")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDto(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Erro de autenticação: " + e.getMessage());
        }
    }
}
