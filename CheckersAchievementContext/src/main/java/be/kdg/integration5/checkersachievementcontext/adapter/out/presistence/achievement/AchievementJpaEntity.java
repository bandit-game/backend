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
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean isAchieved;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private PlayerJpaEntity performer;
}
