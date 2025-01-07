package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.util;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.AchievementJpaEntity;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.types.MovePieceAchievementJpaEntity;
import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.types.PlayNGamesAchievementJpaEntity;
import be.kdg.integration5.checkersachievementcontext.domain.GameId;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.Achievement;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.cumulativeachievement_types.PlayNGamesAchievement;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.gamestateachievement_types.MovePieceAchievement;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AchievementJpaConverter {

    // TODO
    //  Implement proper solution with Strategy pattern if time allows
    public AchievementJpaEntity toJpa(@NonNull Achievement achievement) {
        if (achievement instanceof PlayNGamesAchievement playNGamesAchievement) {
            return new PlayNGamesAchievementJpaEntity(
                    playNGamesAchievement.getName(),
                    playNGamesAchievement.getDescription(),
                    playNGamesAchievement.getImagUrl(),
                    playNGamesAchievement.isAchieved(),
                    null,
                    playNGamesAchievement.getGoal(),
                    playNGamesAchievement.getCounter().stream().map(GameId::uuid).collect(Collectors.toSet())
            );
        } else if (achievement instanceof MovePieceAchievement) {
            return new MovePieceAchievementJpaEntity(
                    achievement.getName(),
                    achievement.getDescription(),
                    achievement.getImagUrl(),
                    achievement.isAchieved(),
                    null
            );
        }

        throw new RuntimeException("No compatible achievement type found");
    }


    // TODO
    //  Implement proper solution with Strategy pattern if time allows
    public Achievement toDomain(@NonNull AchievementJpaEntity achievementJpaEntity) {
        if (achievementJpaEntity instanceof PlayNGamesAchievementJpaEntity playNGamesAchievementJpaEntity) {
            return new PlayNGamesAchievement(
                    playNGamesAchievementJpaEntity.getName(),
                    playNGamesAchievementJpaEntity.getDescription(),
                    playNGamesAchievementJpaEntity.getImageUrl(),
                    playNGamesAchievementJpaEntity.isAchieved(),
                    playNGamesAchievementJpaEntity.getDesiredNumberOfGames(),
                    playNGamesAchievementJpaEntity.getGameIdsPlayed().stream().map(GameId::new).collect(Collectors.toSet())
            );
        } else if (achievementJpaEntity instanceof MovePieceAchievementJpaEntity) {
            return new MovePieceAchievement(
                    achievementJpaEntity.getName(),
                    achievementJpaEntity.getDescription(),
                    achievementJpaEntity.getImageUrl(),
                    achievementJpaEntity.isAchieved()
            );
        }

        throw new RuntimeException("No compatible achievement type found");
    }
}
