package school.sptech.Simbiosys.core.domain;

public class Usuario {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String cargo;
    private String email;
    private String senha;
    private String token;

    public Usuario() {
    }

    public Usuario(Integer id, String nome, String sobrenome, String cargo, String email, String senha, String token) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cargo = cargo;
        this.email = email;
        this.senha = senha;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
