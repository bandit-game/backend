package be.kdg.integration5.statisticscontext.adapter.out.persistencte.predictions;


import be.kdg.integration5.statisticscontext.adapter.out.persistencte.player.PlayerJpaEntity;
import be.kdg.integration5.statisticscontext.domain.Predictions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "player_predicitons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PredictionsJpaEntity {

    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private PlayerJpaEntity player;

    @Column(nullable = false)
    private double churn;

    @Column(nullable = false)
    private double firstMoveWinProbability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Predictions.PlayerClass playerClass;

    public PredictionsJpaEntity(double firstMoveWinProbability, Predictions.PlayerClass playerClass, double churn) {
        this.firstMoveWinProbability = firstMoveWinProbability;
        this.playerClass = playerClass;
        this.churn = churn;
    }
}
