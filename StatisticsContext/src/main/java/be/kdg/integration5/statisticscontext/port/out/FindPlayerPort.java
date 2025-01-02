package be.kdg.integration5.statisticscontext.port.out;

import be.kdg.integration5.statisticscontext.domain.Player;
import be.kdg.integration5.statisticscontext.domain.PlayerId;

import java.util.List;

public interface FindPlayerPort {
    List<Player> findPlayersByIds(List<PlayerId> playerIds);
}
