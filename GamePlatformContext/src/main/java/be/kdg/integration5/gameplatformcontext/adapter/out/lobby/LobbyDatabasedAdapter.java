package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.port.out.FindLobbyPort;
import be.kdg.integration5.gameplatformcontext.port.out.PersistLobbyPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LobbyDatabasedAdapter implements FindLobbyPort, PersistLobbyPort {
    @Override
    public List<Lobby> findAllNotFilledNonPrivateLobbiesByGameId(GameId gameId) {
        return List.of();
    }

    @Override
    public Lobby save(Lobby lobby) {
        return null;
    }
}
