package school.sptech.Simbiosys.controller.dto;

import school.sptech.Simbiosys.enums.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
}
