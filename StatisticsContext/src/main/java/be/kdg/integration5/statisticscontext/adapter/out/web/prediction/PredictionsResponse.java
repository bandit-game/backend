package be.kdg.integration5.statisticscontext.adapter.out.web.prediction;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record PredictionsResponse(List<PlayerPredictionsBody> predictions) {
    public record PlayerPredictionsBody(UUID playerId, double churn, double firstMoveWinProbability, String playerClass) {
        public PlayerPredictionsBody {
            Objects.requireNonNull(playerId);
            Objects.requireNonNull(playerClass);
        }
    }
}
