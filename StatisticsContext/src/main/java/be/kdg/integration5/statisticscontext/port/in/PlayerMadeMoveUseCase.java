package be.kdg.integration5.statisticscontext.port.in;

import be.kdg.integration5.common.events.PlayerMoveEvent;

public interface PlayerMadeMoveUseCase {
    void saveMove(PlayerMoveEvent event);
}
