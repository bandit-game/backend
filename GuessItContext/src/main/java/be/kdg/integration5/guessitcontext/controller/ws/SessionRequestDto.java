package be.kdg.integration5.guessitcontext.controller.ws;

import java.util.Objects;
import java.util.UUID;

public record SessionRequestDto(UUID sessionId) {
    public SessionRequestDto {
        Objects.requireNonNull(sessionId);
    }
}
