package school.sptech.Simbiosys.controller.dto;

public class LoginResponseDto {

    private String token;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponseDto{" +
                "token='" + token + '\'' +
                '}';
    }
}
