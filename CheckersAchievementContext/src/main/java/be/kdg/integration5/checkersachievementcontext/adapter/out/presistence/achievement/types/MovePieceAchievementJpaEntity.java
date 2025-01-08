package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.types;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.AchievementJpaEntity;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.player.PlayerJpaEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("MOVEPIECE")
public class MovePieceAchievementJpaEntity extends AchievementJpaEntity {
    public MovePieceAchievementJpaEntity() {
    }

    public MovePieceAchievementJpaEntity(String name, String description, String imageUrl, boolean isAchieved, PlayerJpaEntity performer) {
        super(name, description, imageUrl, isAchieved, performer);
    }
}
