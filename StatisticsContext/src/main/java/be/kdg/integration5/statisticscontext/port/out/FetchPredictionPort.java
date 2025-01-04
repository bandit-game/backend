package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Metrics;
import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.Predictions;

import java.util.List;
import java.util.Map;

public interface FetchPredictionPort {
    Map<Player, Predictions> fetchPredictions(List<Player> players);
}
