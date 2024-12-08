package be.kdg.integration5.gameplatformcontext.adapter.out.lobby_player;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LobbyPlayerId implements Serializable {
    private UUID lobbyId;
    private UUID playerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyPlayerId that = (LobbyPlayerId) o;
        return Objects.equals(lobbyId, that.lobbyId) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lobbyId, playerId);
    }
}
