package school.sptech.Simbiosys.core.enums;

public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static String converterParaString(UserRole tipo){
        return tipo.toString();
    }
}
