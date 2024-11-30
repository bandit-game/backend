package be.kdg.integration5.gameplatformcontext.port.in;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;

public interface LeaveTheLobbyUseCase {

    Lobby removePlayerFromLobby(PlayerId playerId);
}
