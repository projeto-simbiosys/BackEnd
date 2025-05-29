package school.sptech.Simbiosys.dto;


public class UsuarioRequestDto {

    private String nome;
    private String sobrenome;
    private String cargo;
    private String email;
    private String senha;
    private String token;

    public UsuarioRequestDto(String nome, String sobrenome, String cargo, String email, String senha, String token) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cargo = cargo;
        this.email = email;
        this.senha = senha;
        this.token = token;
    }

    public UsuarioRequestDto() {

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

    @Override
    public String toString() {
        return "UsuarioRequestDto{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}