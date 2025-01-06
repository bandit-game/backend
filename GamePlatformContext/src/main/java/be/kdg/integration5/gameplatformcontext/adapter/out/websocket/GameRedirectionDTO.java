package be.kdg.integration5.gameplatformcontext.adapter.out.websocket;

import java.util.Objects;

public record GameRedirectionDTO(String frontendUrl) {
    public GameRedirectionDTO {
        Objects.requireNonNull(frontendUrl);
    }
}
