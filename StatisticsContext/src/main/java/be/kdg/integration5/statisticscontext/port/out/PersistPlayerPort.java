package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Player;

import java.util.List;

public interface PersistPlayerPort {
    void updateAll(List<Player> players);
}
