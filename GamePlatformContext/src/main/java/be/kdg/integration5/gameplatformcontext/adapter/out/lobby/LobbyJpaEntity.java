package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import be.kdg.integration5.gameplatformcontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private GameJpaEntity game;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlayerJpaEntity lobbyOwner;

    @ManyToMany
    @JoinTable(
            name = "lobby_players",
            joinColumns = @JoinColumn(name = "lobbyId"),
            inverseJoinColumns = @JoinColumn(name = "playerId")
    )
    private List<PlayerJpaEntity> players;


    public LobbyJpaEntity(UUID lobbyId, boolean isPrivate) {
        this.lobbyId = lobbyId;
        this.isPrivate = isPrivate;
    }

    public Lobby toDomain(Game game) {
        return new Lobby(
                new LobbyId(lobbyId),
                isPrivate,
                game,
                lobbyOwner.toDomain(),
                new ArrayList<>(players.stream().map(PlayerJpaEntity::toDomain).toList())
        );
    }

    public static LobbyJpaEntity of(Lobby lobby) {
        return new LobbyJpaEntity(
                lobby.getLobbyId().uuid(),
                lobby.isPrivate()
        );
    }
}
