package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Session;

import java.io.OutputStream;
import java.util.List;

public interface ExportSessionPort {
    void exportEntities(List<Session> sessions, OutputStream outputStream);
}
