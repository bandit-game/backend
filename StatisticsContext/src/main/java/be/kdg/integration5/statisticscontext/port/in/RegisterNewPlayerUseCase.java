package be.kdg.integration5.statisticscontext.port.in;


import be.kdg.integration5.common.events.statistics.NewPlayerRegisteredEvent;
import be.kdg.integration5.statisticscontext.domain.Player;

public interface RegisterNewPlayerUseCase {
    Player register(NewPlayerRegisteredEvent event);
}
