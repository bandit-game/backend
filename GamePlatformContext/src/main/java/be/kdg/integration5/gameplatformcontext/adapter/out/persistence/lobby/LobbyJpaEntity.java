package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.lobby;

import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.game.GameJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.lobby_player.LobbyPlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player.PlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lobbies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LobbyJpaEntity {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID lobbyId;

    @Column(nullable = false)
    private boolean isPrivate;

    @Column(nullable = false)
    private boolean isReady;

    @ManyToOne(fetch = FetchType.LAZY)
    private GameJpaEntity game;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlayerJpaEntity lobbyOwner;

    @OneToMany(mappedBy = "lobby")
    private List<LobbyPlayerJpaEntity> lobbyPlayers; // This represents the players in the lobby



    public LobbyJpaEntity(UUID lobbyId, boolean isPrivate, boolean isReady) {
        this.lobbyId = lobbyId;
        this.isPrivate = isPrivate;
        this.isReady = isReady;
    }

    public Lobby toDomain(Game game, List<Player> players) {
        return new Lobby(
                new LobbyId(lobbyId),
                isPrivate,
                game,
                lobbyOwner.toDomain(),
                players,
                isReady
        );
    }

    public static LobbyJpaEntity of(Lobby lobby) {
        return new LobbyJpaEntity(
                lobby.getLobbyId().uuid(),
                lobby.isPrivate(),
                lobby.isReady()
        );
    }
}
