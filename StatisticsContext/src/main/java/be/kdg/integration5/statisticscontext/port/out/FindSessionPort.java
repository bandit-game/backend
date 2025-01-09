package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Session;
import be.kdg.integration5.statisticscontext.domain.SessionId;

import java.time.LocalDateTime;
import java.util.List;

public interface FindSessionPort {
    Session findById(SessionId id);
    List<Session> findAllByNotFinishTime(LocalDateTime finishTime);
}
