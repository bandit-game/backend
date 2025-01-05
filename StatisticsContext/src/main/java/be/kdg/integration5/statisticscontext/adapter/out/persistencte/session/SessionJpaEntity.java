package be.kdg.integration5.statisticscontext.adapter.out.persistencte.session;


import be.kdg.integration5.statisticscontext.adapter.out.persistencte.game.GameJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.persistencte.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.persistencte.player_session.PlayerSessionJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionJpaEntity {

    @Id
    @Column(name = "session_id")
    private UUID sessionId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column()
    private LocalDateTime finishTime;

    @Column(nullable = false)
    private boolean isDraw;

    @OneToMany(mappedBy = "session")
    private Set<PlayerSessionJpaEntity> players;

    @ManyToOne(fetch = FetchType.LAZY)
    private GameJpaEntity game;

    @OneToOne(fetch = FetchType.LAZY)
    private PlayerJpaEntity winner;

    public SessionJpaEntity(LocalDateTime startTime, UUID sessionId, LocalDateTime finishTime, boolean isDraw) {
        this.startTime = startTime;
        this.sessionId = sessionId;
        this.finishTime = finishTime;
        this.isDraw = isDraw;
    }
}
