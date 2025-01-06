package be.kdg.integration5.statisticscontext.port.in;

import java.io.OutputStream;

public interface CollectPlayerStatisticsCsvUseCase {
    void exportPlayerData(OutputStream outputStream);
}
