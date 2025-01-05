package be.kdg.integration5.statisticscontext.core;

import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.port.in.CollectSessionStatisticsCsvUseCase;
import be.kdg.integration5.statisticscontext.port.out.ExportSessionPort;
import be.kdg.integration5.statisticscontext.port.out.FindSessionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
@Transactional
public class CollectSessionStatisticsCsvUseCaseImpl implements CollectSessionStatisticsCsvUseCase {

    private final FindSessionPort findSessionPort;
    private final ExportSessionPort exportSessionPort;

    public CollectSessionStatisticsCsvUseCaseImpl(FindSessionPort findSessionPort, ExportSessionPort exportSessionPort) {
        this.findSessionPort = findSessionPort;
        this.exportSessionPort = exportSessionPort;
    }

    @Override
    public void exportSessionData(OutputStream outputStream) {
        List<Session> allSessions = findSessionPort.findAllByNotFinishTime(null);
        exportSessionPort.exportEntities(allSessions, outputStream);
    }
}
