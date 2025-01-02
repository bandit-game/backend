package be.kdg.integration5.statisticscontext.adapter.out.player_session;


import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.session.SessionJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "player_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSessionJpaEntity {

    @EmbeddedId
    private PlayerSessionId playerSessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sessionId")
    private SessionJpaEntity session;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    private PlayerJpaEntity player;

    public PlayerSessionJpaEntity(PlayerSessionId playerSessionId) {
        this.playerSessionId = playerSessionId;
    }

}
