package school.sptech.Simbiosys.core.dto;

public class AlterarSenhaDto {
    private String email;
    private String novaSenha;

    public String getNovaSenha() {
        return novaSenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
