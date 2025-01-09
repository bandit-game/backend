package be.kdg.integration5.statisticscontext.adapter.out.web.prediction;

import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.Predictions;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PredictionsWebConverter {

    public PredictionsRequest toRequest(List<Player> players) {
        return new PredictionsRequest(
                Predictions.getPredictionsList(),
                players.stream().map(p ->
                        new PredictionsRequest.PlayerMetricsBody(
                                p.getPlayerId().uuid(),
                                p.getGender().name(),
                                p.getAge(),
                                p.getLocation().country(),
                                p.getLocation().city(),
                                p.getMetrics().getTotalGamesPlayed(),
                                p.getMetrics().getTotalWins(),
                                p.getMetrics().getTotalLosses(),
                                p.getMetrics().getTotalDraws(),
                                p.getMetrics().getTotalIsFirst(),
                                p.getMetrics().getAvgMoveDuration(),
                                p.getMetrics().getAvgMoveAmount(),
                                p.getMetrics().getAvgGameDuration(),
                                p.getMetrics().getTotalWeekdaysPlayed(),
                                p.getMetrics().getTotalWeekendsPlayed(),
                                p.getMetrics().getTotalMorningPlays(),
                                p.getMetrics().getTotalAfternoonPlays(),
                                p.getMetrics().getTotalEveningPlays(),
                                p.getMetrics().getTotalNightPlays()
                        )
                ).toList()
        );
    }

    public Map<Player, Predictions> toMap(PredictionsResponse response, List<Player> players) {
        Map<UUID, Player> playerMap = players.stream()
                .collect(Collectors.toMap(player -> player.getPlayerId().uuid(), player -> player));
        return response.predictions().stream()
                .filter(prediction -> playerMap.containsKey(prediction.playerId())) // Only include valid player IDs
                .collect(Collectors.toMap(
                        prediction -> playerMap.get(prediction.playerId()), // Map to the corresponding Player
                        prediction -> new Predictions(
                                prediction.churn(),
                                prediction.firstMoveWinProbability(),
                                Predictions.PlayerClass.valueOf(prediction.playerClass()) // Convert String to Enum
                        )
                ));
    }
}
