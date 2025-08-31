package school.sptech.Simbiosys.core.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;

import java.util.Collection;

public class UsuarioDetalhesDto implements UserDetails {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String cargo;
    private String email;
    private String senha;

    public UsuarioDetalhesDto(UsuarioEntity usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.sobrenome = usuario.getSobrenome();
        this.cargo = usuario.getCargo();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
