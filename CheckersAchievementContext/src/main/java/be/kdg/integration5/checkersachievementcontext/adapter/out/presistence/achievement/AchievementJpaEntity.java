package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.player.PlayerJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "achievements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class AchievementJpaEntity {
    @EmbeddedId
    private AchievementJpaEntityId achievementId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean isAchieved;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    @MapsId("performer_id")
    private PlayerJpaEntity performer;

    public AchievementJpaEntity(String name, String description, String imageUrl, boolean isAchieved, PlayerJpaEntity performer) {
        this.achievementId = new AchievementJpaEntityId(name, performer.getPlayerId());
        this.description = description;
        this.imageUrl = imageUrl;
        this.isAchieved = isAchieved;
        this.performer = performer;
    }
}
