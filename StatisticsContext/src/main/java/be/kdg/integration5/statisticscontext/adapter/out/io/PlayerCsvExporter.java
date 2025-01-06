package be.kdg.integration5.statisticscontext.adapter.out.io;

import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.port.out.ExportPlayerPort;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Component
public class PlayerCsvExporter implements ExportPlayerPort {

    private final PlayerCsvConverter playerCsvConverter;

    public PlayerCsvExporter(PlayerCsvConverter playerCsvConverter) {
        this.playerCsvConverter = playerCsvConverter;
    }

    @Override
    public void exportPlayerMetrics(List<Player> players, OutputStream outputStream) {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
            List<String[]> convertedSessions = playerCsvConverter.toCsvRows(players);
            String[] header = {
                    "PlayerId",
                    "Country",
                    "City",
                    "Age",
                    "Gender",
                    "TotalGamesPlayed",
                    "TotalWins",
                    "TotalLosses",
                    "TotalDraws",
                    "TotalIsFirst",
                    "AvgMoveDuration",
                    "TotalWeekdaysPlayed",
                    "TotalWeekendsPlayed",
                    "TotalMorningPlays",
                    "TotalAfternoonPlays",
                    "TotalEveningPlays",
                    "TotalNightPlays",
                    "Churn",
                    "FirstMoveWinProbability",
                    "PlayerClass"

            };
            writer.writeNext(header);
            convertedSessions.forEach(writer::writeNext);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write entities to CSV", e);
        }
    }
}
