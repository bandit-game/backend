package be.kdg.integration5.statisticscontext.adapter.out.move;


import be.kdg.integration5.statisticscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.session.SessionJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "moves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoveJpaEntity {

    @Id
    @Column(name = "move_id")
    private UUID moveId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column()
    private LocalDateTime endTime;

    @Column(nullable = false)
    private int moveNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlayerSessionJpaEntity playerSession;

    public MoveJpaEntity(UUID moveId, int moveNumber, LocalDateTime endTime, LocalDateTime startTime) {
        this.moveId = moveId;
        this.moveNumber = moveNumber;
        this.endTime = endTime;
        this.startTime = startTime;
    }
}
