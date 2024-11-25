package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.Player;

import java.util.List;

public interface FindLobbyPort {
    List<Lobby> findAllNotFilledNonPrivateLobbiesByGameId(GameId gameId);
}
