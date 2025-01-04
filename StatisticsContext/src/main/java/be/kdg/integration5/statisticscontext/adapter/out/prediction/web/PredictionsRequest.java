package be.kdg.integration5.statisticscontext.adapter.out.prediction.web;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record PredictionsRequest(List<String> predictionVariables, List<PlayerMetricsBody> playerMetrics) {
    public record PlayerMetricsBody(
            UUID playerId,
            String gender,
            int age,
            String country,
            String city,
            int totalGamesPlayed,
            int totalWins,
            int totalLosses,
            int totalDraws,
            int totalIsFirst,
            double avgMoveDuration,
            double avgMoveAmount,
            double avgGameDuration,
            int totalWeekdaysPlayed,
            int totalWeekendsPlayed,
            int totalMorningPlays,
            int totalAfternoonPlays,
            int totalEveningPlays,
            int totalNightPlays
    ) {
        public PlayerMetricsBody {
            Objects.requireNonNull(playerId);
            Objects.requireNonNull(gender);
            Objects.requireNonNull(country);
            Objects.requireNonNull(city);
        }
    }
}
