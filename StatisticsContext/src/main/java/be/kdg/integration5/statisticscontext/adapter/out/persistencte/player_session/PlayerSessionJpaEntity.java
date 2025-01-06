package be.kdg.integration5.statisticscontext.adapter.out.persistencte.player_session;


import be.kdg.integration5.statisticscontext.adapter.out.persistencte.move.MoveJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.persistencte.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.persistencte.session.SessionJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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

    @OneToMany(mappedBy = "playerSession")
    private Set<MoveJpaEntity> moves;

    public PlayerSessionJpaEntity(PlayerSessionId playerSessionId) {
        this.playerSessionId = playerSessionId;
    }

}
