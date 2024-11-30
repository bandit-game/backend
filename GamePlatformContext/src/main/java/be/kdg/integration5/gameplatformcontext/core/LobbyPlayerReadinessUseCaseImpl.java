package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.LobbyId;
import be.kdg.integration5.gameplatformcontext.port.in.LobbyPlayersReadinessUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.FindLobbyPort;
import be.kdg.integration5.gameplatformcontext.port.out.NotifyGameStartPort;
import be.kdg.integration5.gameplatformcontext.port.out.NotifyLobbyUpdatePort;
import be.kdg.integration5.gameplatformcontext.port.out.PersistLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LobbyPlayerReadinessUseCaseImpl implements LobbyPlayersReadinessUseCase {

    private final FindLobbyPort findLobbyPort;
    private final PersistLobbyPort persistLobbyPort;
    private final NotifyLobbyUpdatePort notifyLobbyUpdatePort;
    private final NotifyGameStartPort gameStartPort;

    public LobbyPlayerReadinessUseCaseImpl(FindLobbyPort findLobbyPort, PersistLobbyPort persistLobbyPort, NotifyLobbyUpdatePort notifyLobbyUpdatePort, NotifyGameStartPort gameStartPort) {
        this.findLobbyPort = findLobbyPort;
        this.persistLobbyPort = persistLobbyPort;
        this.notifyLobbyUpdatePort = notifyLobbyUpdatePort;
        this.gameStartPort = gameStartPort;
    }


    @Override
    @Transactional
    public void getGameUrlForPlayers(LobbyId lobbyId) {
        Lobby lobby = findLobbyPort.findLobbyById(lobbyId);
        lobby.readyForGame();
        persistLobbyPort.update(lobby);
        notifyLobbyUpdatePort.sendGameRedirectionLink(lobby, lobby.getPlayingGame());
        gameStartPort.notifyGameStart(lobby);

    }
}
