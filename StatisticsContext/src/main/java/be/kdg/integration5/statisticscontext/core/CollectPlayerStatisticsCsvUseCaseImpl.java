package be.kdg.integration5.statisticscontext.core;

import be.kdg.integration5.statisticscontext.adapter.out.io.PlayerCsvExporter;
import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.port.in.CollectPlayerStatisticsCsvUseCase;
import be.kdg.integration5.statisticscontext.port.out.FindPlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Transactional
@Service
public class CollectPlayerStatisticsCsvUseCaseImpl implements CollectPlayerStatisticsCsvUseCase {

    private final PlayerCsvExporter playerCsvExporter;
    private final FindPlayerPort findPlayerPort;

    public CollectPlayerStatisticsCsvUseCaseImpl(PlayerCsvExporter playerCsvExporter, FindPlayerPort findPlayerPort) {
        this.playerCsvExporter = playerCsvExporter;
        this.findPlayerPort = findPlayerPort;
    }

    @Override
    public void exportPlayerData(OutputStream outputStream) {
        List<Player> allPlayers = findPlayerPort.findAllFetched();
        playerCsvExporter.exportPlayerMetrics(allPlayers, outputStream);
    }
}
