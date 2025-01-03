package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.SessionId;

import java.util.List;

public interface PersistPlayerPort {
    void updateAllInSession(List<Player> players, SessionId sessionId);
}
