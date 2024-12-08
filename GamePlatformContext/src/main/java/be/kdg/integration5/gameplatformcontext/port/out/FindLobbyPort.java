package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.*;

import java.util.List;

public interface FindLobbyPort {
    List<Lobby> findAllNotFilledNonPrivateLobbiesByGameId(GameId gameId, boolean isPrivate);
    Lobby findLobbyByPlayerAndReadiness(Player player, boolean isReady);
    Lobby findLobbyById(LobbyId lobbyId);
}
