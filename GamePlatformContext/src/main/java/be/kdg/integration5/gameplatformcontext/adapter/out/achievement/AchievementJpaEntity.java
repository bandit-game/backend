package be.kdg.integration5.gameplatformcontext.adapter.out.achievement;

import be.kdg.integration5.gameplatformcontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.gameplatformcontext.domain.Achievement;
import be.kdg.integration5.gameplatformcontext.domain.AchievementId;
import be.kdg.integration5.gameplatformcontext.domain.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "achievements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementJpaEntity {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    public UUID achievementId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gameId", referencedColumnName = "gameId")
    private GameJpaEntity game;

    public Achievement toDomain() {
        return new Achievement(
                new AchievementId(this.achievementId),
                this.name,
                this.description,
                new Image(this.imageUrl)
        );
    }
}
