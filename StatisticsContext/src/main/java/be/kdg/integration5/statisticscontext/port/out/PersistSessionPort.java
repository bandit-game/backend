package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Session;

public interface PersistSessionPort {
    Session save(Session session);
    Session update(Session session);
}
