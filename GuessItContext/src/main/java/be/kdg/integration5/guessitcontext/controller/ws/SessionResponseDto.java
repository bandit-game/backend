package be.kdg.integration5.guessitcontext.controller.ws;

import java.util.UUID;

public record SessionResponseDto(UUID sessionId, UUID currentPlayerId, boolean isFinished, UUID winnerId) {
}
