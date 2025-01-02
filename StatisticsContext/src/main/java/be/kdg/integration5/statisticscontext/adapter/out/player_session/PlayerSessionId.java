package be.kdg.integration5.statisticscontext.adapter.out.player_session;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSessionId {

    private UUID sessionId;
    private UUID playerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSessionId that = (PlayerSessionId) o;
        return Objects.equals(sessionId, that.sessionId) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, playerId);
    }
}
