package be.kdg.integration5.statisticscontext.adapter.out.io;

import be.kdg.integration5.statisticscontext.domain.Metrics;
import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.Session;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerCsvConverter {

    public List<String[]> toCsvRows(List<Player> players) {
        List<String[]> rows = new ArrayList<>();
        for (Player player: players) {
            Metrics metrics = player.getMetrics();
            rows.add(new String[]{
                    player.getPlayerId().uuid().toString(),
                    player.getLocation().country(),
                    player.getLocation().city(),
                    Integer.toString(player.getAge()),
                    player.getGender().name(),
                    Integer.toString(metrics.getTotalGamesPlayed()),
                    Integer.toString(metrics.getTotalWins()),
                    Integer.toString(metrics.getTotalLosses()),
                    Integer.toString(metrics.getTotalDraws()),
                    Integer.toString(metrics.getTotalIsFirst()),
                    Double.toString(metrics.getAvgMoveDuration()),
                    Double.toString(metrics.getAvgMoveAmount()),
                    Double.toString(metrics.getAvgGameDuration()),
                    Integer.toString(metrics.getTotalWeekdaysPlayed()),
                    Integer.toString(metrics.getTotalWeekendsPlayed()),
                    Integer.toString(metrics.getTotalMorningPlays()),
                    Integer.toString(metrics.getTotalAfternoonPlays()),
                    Integer.toString(metrics.getTotalEveningPlays()),
                    Integer.toString(metrics.getTotalNightPlays()),
            });
        }
        return rows;
    }
}
