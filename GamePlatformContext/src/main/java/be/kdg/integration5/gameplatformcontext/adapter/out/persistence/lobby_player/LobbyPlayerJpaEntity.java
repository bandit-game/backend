package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.lobby_player;

import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.lobby.LobbyJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player.PlayerJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lobby_players")
public class LobbyPlayerJpaEntity {
    @EmbeddedId
    private LobbyPlayerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("lobbyId")
    private LobbyJpaEntity lobby;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    private PlayerJpaEntity player;

    public LobbyPlayerJpaEntity(LobbyPlayerId id) {
        this.id = id;
    }

    public static LobbyPlayerJpaEntity of(LobbyPlayerId lobbyPlayerId) {
        return new LobbyPlayerJpaEntity(lobbyPlayerId);
    }
}
