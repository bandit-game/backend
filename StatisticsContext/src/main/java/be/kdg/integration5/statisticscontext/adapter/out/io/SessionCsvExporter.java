package be.kdg.integration5.statisticscontext.adapter.out.io;

import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.port.out.ExportSessionPort;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Component
public class SessionCsvExporter implements ExportSessionPort {
    private final SessionCsvConverter sessionCsvConverter;

    public SessionCsvExporter(SessionCsvConverter sessionCsvConverter) {
        this.sessionCsvConverter = sessionCsvConverter;
    }

    @Override
    public void exportEntities(List<Session> sessions, OutputStream outputStream) {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
            List<String[]> convertedSessions = sessionCsvConverter.toCsvRows(sessions);
            String[] header = {
                    "SessionDuration",
                    "GameId",
                    "IsDraw",
                    "NumberOfMoves",
                    "PlayerId",
                    "SessionId",
                    "IsWinner",
                    "Gender",
                    "Age",
                    "Country",
                    "City",
                    "PlayedDateTime",
                    "AvgMoveTime",
                    "IsFirst"

            };
            writer.writeNext(header);
            convertedSessions.forEach(writer::writeNext);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write entities to CSV", e);
        }
    }

}
