package be.kdg.integration5.statisticscontext.port.in;

import java.io.OutputStream;

public interface CollectSessionStatisticsCsvUseCase {
    void exportSessionData(OutputStream outputStream);
}
