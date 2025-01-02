package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.domain.SessionId;

public interface FindSessionPort {
    Session findById(SessionId id);
}
