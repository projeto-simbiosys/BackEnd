package school.sptech.Simbiosys.core.dto;

import school.sptech.Simbiosys.infrastructure.persistence.entity.UsuarioEntity;


public class UsuarioResponseDto {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String cargo;
    private String email;

    public UsuarioResponseDto(UsuarioEntity usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.sobrenome = usuario.getSobrenome();
        this.cargo = usuario.getCargo();
        this.email = usuario.getEmail();
    }

    public UsuarioResponseDto() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


