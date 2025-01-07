package be.kdg.integration5.checkersachievementcontext.domain.achievement;

import be.kdg.integration5.checkersachievementcontext.domain.achievement.cumulativeachievement_types.PlayNGamesAchievement;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.gamestateachievement_types.MovePieceAchievement;

import java.util.Set;

public abstract class AchievementsProvider {
    public static final Set<Achievement> ACHIEVEMENTS_SET = Set.of(
            new PlayNGamesAchievement(10),
            new MovePieceAchievement()
    );
}
