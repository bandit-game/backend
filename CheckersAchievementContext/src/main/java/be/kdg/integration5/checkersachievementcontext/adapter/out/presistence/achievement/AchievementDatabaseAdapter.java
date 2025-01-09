package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.util.AchievementJpaConverter;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import be.kdg.integration5.checkersachievementcontext.domain.achievement.Achievement;
import be.kdg.integration5.checkersachievementcontext.port.out.FindAchievementsPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AchievementDatabaseAdapter implements FindAchievementsPort {
    private final AchievementJpaRepository achievementJpaRepository;
    private final AchievementJpaConverter achievementJpaConverter;

    @Autowired
    public AchievementDatabaseAdapter(AchievementJpaRepository achievementJpaRepository, AchievementJpaConverter achievementJpaConverter) {
        this.achievementJpaRepository = achievementJpaRepository;
        this.achievementJpaConverter = achievementJpaConverter;
    }

    @Override
    public List<Achievement> findAllAchievementsForPlayerByIsAchieved(PlayerId playerId, boolean isAchieved) {
        return achievementJpaRepository.findAllByIsAchievedAndPerformer_PlayerId(isAchieved, playerId.uuid()).stream().map(achievementJpaConverter::toDomain).toList();
    }
}
