package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.in.LeaveTheLobbyUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LeaveTheLobbyUseCaseImpl implements LeaveTheLobbyUseCase {
    private final PersistLobbyPort persistLobbyPort;
    private final FindLobbyPort findLobbyPort;
    private final DeleteLobbyPort deleteLobbyPort;
    private final FindPlayerPort findPlayerPort;
    private final NotifyLobbyUpdatePort notifyLobbyUpdatePort;

    public LeaveTheLobbyUseCaseImpl(PersistLobbyPort persistLobbyPort, FindLobbyPort findLobbyPort, DeleteLobbyPort deleteLobbyPort, FindPlayerPort findPlayerPort, NotifyLobbyUpdatePort notifyLobbyUpdatePort) {
        this.persistLobbyPort = persistLobbyPort;
        this.findLobbyPort = findLobbyPort;
        this.deleteLobbyPort = deleteLobbyPort;
        this.findPlayerPort = findPlayerPort;
        this.notifyLobbyUpdatePort = notifyLobbyUpdatePort;
    }

    @Override
    @Transactional
    public Lobby removePlayerFromLobby(PlayerId playerId) {
        Player player = findPlayerPort.findPlayerById(playerId);
        Lobby lobby = findLobbyPort.findLobbyByPlayerAndReadiness(player, false);

        lobby.removePlayer(player);

        if (lobby.isEmpty()) {
            deleteLobbyPort.deleteLobby(lobby);
        } else {
            persistLobbyPort.update(lobby);
            notifyLobbyUpdatePort.notifyAllPlayersInLobby(lobby);
        }

        return lobby;
    }
}
