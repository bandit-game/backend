package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import be.kdg.integration5.gameplatformcontext.adapter.out.exception.LobbyNotFoundException;
import be.kdg.integration5.gameplatformcontext.adapter.out.exception.PlayerNotFoundException;
import be.kdg.integration5.gameplatformcontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.game.GameJpaRepository;
import be.kdg.integration5.gameplatformcontext.adapter.out.lobby_player.LobbyPlayerId;
import be.kdg.integration5.gameplatformcontext.adapter.out.lobby_player.LobbyPlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.lobby_player.LobbyPlayerJpaRepository;
import be.kdg.integration5.gameplatformcontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.gameplatformcontext.domain.Game;
import be.kdg.integration5.gameplatformcontext.domain.GameId;
import be.kdg.integration5.gameplatformcontext.domain.Lobby;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.port.out.DeleteLobbyPort;
import be.kdg.integration5.gameplatformcontext.port.out.FindLobbyPort;
import be.kdg.integration5.gameplatformcontext.port.out.PersistLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LobbyDatabaseAdapter implements FindLobbyPort, PersistLobbyPort, DeleteLobbyPort {

    private final LobbyJpaRepository lobbyJpaRepository;
    private final GameJpaRepository gameJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;
    private final LobbyPlayerJpaRepository lobbyPlayerJpaRepository;

    public LobbyDatabaseAdapter(LobbyJpaRepository lobbyJpaRepository, GameJpaRepository gameJpaRepository, PlayerJpaRepository playerJpaRepository, LobbyPlayerJpaRepository lobbyPlayerJpaRepository) {
        this.lobbyJpaRepository = lobbyJpaRepository;
        this.gameJpaRepository = gameJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
        this.lobbyPlayerJpaRepository = lobbyPlayerJpaRepository;
    }

    @Override
    public List<Lobby> findAllNotFilledNonPrivateLobbiesByGameId(GameId gameId, boolean isPrivate) {
        Game game = gameJpaRepository.getReferenceById(gameId.uuid()).toDomain();
        List<LobbyJpaEntity> lobbyJpaEntities = lobbyJpaRepository
                .findAllNotFilledNonPrivateLobbiesByGameIdCustom(gameId.uuid(), isPrivate);

        return lobbyJpaEntities.stream()
                .map(l -> l.toDomain(
                        game,
                        l.getLobbyPlayers().stream()
                                .map(lp -> lp.getPlayer().toDomain())
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Lobby save(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = LobbyJpaEntity.of(lobby);
        PlayerJpaEntity ownerJpaEntity = playerJpaRepository.getReferenceById(lobby.getLobbyOwner().getPlayerId().uuid());
        GameJpaEntity gameJpaEntity = gameJpaRepository.getReferenceById(lobby.getPlayingGame().getGameId().uuid());


        lobbyJpaEntity.setLobbyOwner(ownerJpaEntity);
        lobbyJpaEntity.setGame(gameJpaEntity);
        LobbyJpaEntity savedLobbyJpaEntity = lobbyJpaRepository.save(lobbyJpaEntity);

        for (Player player : lobby.getPlayers()) {
            PlayerJpaEntity playerJpaEntity = playerJpaRepository.getReferenceById(player.getPlayerId().uuid());
            LobbyPlayerId lobbyPlayerId = new LobbyPlayerId(savedLobbyJpaEntity.getLobbyId(), player.getPlayerId().uuid());
            LobbyPlayerJpaEntity lobbyPlayerJpaEntity = new LobbyPlayerJpaEntity(lobbyPlayerId);
            lobbyPlayerJpaEntity.setLobby(savedLobbyJpaEntity);
            lobbyPlayerJpaEntity.setPlayer(playerJpaEntity);
            lobbyPlayerJpaRepository.save(lobbyPlayerJpaEntity);
        }

        return savedLobbyJpaEntity.toDomain(gameJpaEntity.toDomain(), lobby.getPlayers());
    }

    @Override
    public Lobby update(Lobby lobby) {
        LobbyJpaEntity lobbyJpaEntity = lobbyJpaRepository.getReferenceById(lobby.getLobbyId().uuid());
        PlayerJpaEntity lobbyOwner = playerJpaRepository.getReferenceById(lobby.getLobbyOwner().getPlayerId().uuid());
        lobbyJpaEntity.setLobbyOwner(lobbyOwner);
        lobbyJpaEntity.setReady(lobby.isReady());

        List<LobbyPlayerJpaEntity> existingPlayers = lobbyPlayerJpaRepository.findByLobbyIdFetched(lobby.getLobbyId().uuid());

        Set<UUID> newPlayerIds = lobby.getPlayers().stream()
                .map(player -> player.getPlayerId().uuid())
                .collect(Collectors.toSet());

        List<LobbyPlayerJpaEntity> playersToRemove = existingPlayers.stream()
                .filter(lp -> !newPlayerIds.contains(lp.getPlayer().getPlayerId()))
                .collect(Collectors.toList());

        lobbyPlayerJpaRepository.deleteAll(playersToRemove);

        return lobbyJpaRepository.save(lobbyJpaEntity).toDomain(lobby.getPlayingGame(), lobby.getPlayers());
    }

    @Override
    public Lobby findLobbyByPlayerAndReadiness(Player player, boolean isReady) {
        LobbyJpaEntity lobbyJpaEntity = lobbyJpaRepository.findByPlayerIdAndIsReadyCustom(player.getPlayerId().uuid(), isReady)
                .orElseThrow(() -> new LobbyNotFoundException(
                        "Lobby for player [%s] and readiness [%s] not found".formatted(
                                player.getPlayerId().uuid(),
                                isReady)));
        List<Player> players = lobbyJpaEntity.getLobbyPlayers().stream().map(lp -> lp.getPlayer().toDomain()).collect(Collectors.toList());
        return lobbyJpaEntity.toDomain(lobbyJpaEntity.getGame().toDomain(), players);
    }

    @Override
    public void deleteLobby(Lobby lobby) {
        lobbyJpaRepository.deleteById(lobby.getLobbyId().uuid());
    }
}
