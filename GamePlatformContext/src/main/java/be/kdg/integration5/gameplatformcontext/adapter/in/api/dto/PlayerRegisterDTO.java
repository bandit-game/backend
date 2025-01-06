package be.kdg.integration5.gameplatformcontext.adapter.in.api.dto;

import java.util.Objects;

public record PlayerRegisterDTO(String token) {
    public PlayerRegisterDTO {
        Objects.requireNonNull(token);
    }
}
