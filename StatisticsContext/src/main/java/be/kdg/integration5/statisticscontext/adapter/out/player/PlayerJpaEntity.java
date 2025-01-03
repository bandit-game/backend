package be.kdg.integration5.statisticscontext.adapter.out.player;

import be.kdg.integration5.statisticscontext.adapter.out.player_metrics.PlayerMetricsJpaEntity;
import be.kdg.integration5.statisticscontext.adapter.out.player_session.PlayerSessionJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerJpaEntity {

    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(nullable = false)
    private String playerName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @OneToMany(mappedBy = "player")
    private List<PlayerSessionJpaEntity> sessions;

    @OneToOne(mappedBy = "player")
    private PlayerMetricsJpaEntity playerMetrics;

    public PlayerJpaEntity(UUID playerId, String city, String country, String gender, int age, String playerName) {
        this.playerId = playerId;
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.age = age;
        this.playerName = playerName;
    }
}
