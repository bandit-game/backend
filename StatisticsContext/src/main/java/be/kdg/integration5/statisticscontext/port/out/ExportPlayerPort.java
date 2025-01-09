package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Player;

import java.io.OutputStream;
import java.util.List;

public interface ExportPlayerPort {
    void exportPlayerMetrics(List<Player> players, OutputStream outputStream);
}
