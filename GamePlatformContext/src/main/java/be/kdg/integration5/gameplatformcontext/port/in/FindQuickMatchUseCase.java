package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;

public interface FindQuickMatchUseCase {
    Lobby findQuickMatch(FindQuickMatchCommand findQuickMatchCommand);
}
