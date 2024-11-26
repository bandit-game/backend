package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import be.kdg.integration5.gameplatformcontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.game.GameJpaRepository;
import be.kdg.integration5.gameplatformcontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.port.out.FindLobbyPort;
import be.kdg.integration5.gameplatformcontext.port.out.PersistLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LobbyDatabasedAdapter implements FindLobbyPort, PersistLobbyPort {

    private final LobbyJpaRepository lobbyJpaRepository;
    private final GameJpaRepository gameJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;

    public LobbyDatabasedAdapter(LobbyJpaRepository lobbyJpaRepository, GameJpaRepository gameJpaRepository, PlayerJpaRepository playerJpaRepository) {
        this.lobbyJpaRepository = lobbyJpaRepository;
        this.gameJpaRepository = gameJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public List<Lobby> findAllNotFilledNonPrivateLobbiesByGameId(GameId gameId, boolean isPrivate) {
        Game game = gameJpaRepository.getReferenceById(gameId.uuid()).toDomain();
        List<LobbyJpaEntity> lobbyJpaEntities = lobbyJpaRepository
                .findAllNotFilledNonPrivateLobbiesByGameIdCustom(gameId.uuid(), isPrivate);
        return lobbyJpaEntities.stream().map(l -> l.toDomain(game)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Lobby save(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = LobbyJpaEntity.of(lobby);
        PlayerJpaEntity ownerJpaEntity = playerJpaRepository.getReferenceById(lobby.getLobbyOwner().getPlayerId().uuid());
        GameJpaEntity gameJpaEntity = gameJpaRepository.getReferenceById(lobby.getPlayingGame().getGameId().uuid());
        List<PlayerJpaEntity> playersJpaEntities = playerJpaRepository.findAllById(
                lobby.getPlayers()
                        .stream()
                        .map(player -> player.getPlayerId().uuid())
                        .toList()
        );

        lobbyJpaEntity.setLobbyOwner(ownerJpaEntity);
        lobbyJpaEntity.setGame(gameJpaEntity);
        lobbyJpaEntity.setPlayers(playersJpaEntities);
        return lobbyJpaRepository.save(lobbyJpaEntity).toDomain(gameJpaEntity.toDomain());
    }
}
