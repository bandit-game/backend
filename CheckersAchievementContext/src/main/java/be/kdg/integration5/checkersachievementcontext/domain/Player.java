package be.kdg.integration5.checkersachievementcontext.domain;

import be.kdg.integration5.checkersachievementcontext.domain.achievement.Achievement;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.AchievementsProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class Player {
    private PlayerId playerId;
    private Set<Achievement> achievements;

    public Player(PlayerId playerId) {
        this.playerId = playerId;
        this.achievements = AchievementsProvider.ACHIEVEMENTS_SET;
    }

    public void checkAchievementsFulfilled(Game game) {
        achievements.forEach(achievement -> {
            if (achievement.isFulfilled(game, playerId))
                achievement.open();
        });
    }
}
