package be.kdg.integration5.common.events;

import be.kdg.integration5.common.domain.PlayerMatchHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FinishGameSessionEvent(UUID gameId, String gameType, boolean draw, SessionLength sessionLength, List<PlayerResult> playersResults) {

    public record PlayerResult(UUID id, int age, String gender, Location location, int numberOfMoves, boolean win, PlayerMatchHistory extraHistory) {

    }
    public record Location(String city, String country) {

    }
    public record SessionLength(LocalDateTime start, LocalDateTime end) {

    }

}
