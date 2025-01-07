package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.player;

import be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement.util.AchievementJpaConverter;
import be.kdg.integration5.checkersachievementcontext.domain.Player;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlayerJpaConverter {
    private final AchievementJpaConverter achievementJpaConverter;

    @Autowired
    public PlayerJpaConverter(AchievementJpaConverter achievementJpaConverter) {
        this.achievementJpaConverter = achievementJpaConverter;
    }

    public PlayerJpaEntity toJpa(Player player) {
        return new PlayerJpaEntity(
                player.getPlayerId().uuid(),
                player.getAchievements().stream().map(achievementJpaConverter::toJpa).collect(Collectors.toSet())
        );
    }

    public Player toDomain(PlayerJpaEntity playerJpaEntity) {
        return new Player(
                new PlayerId(playerJpaEntity.getPlayerId()),
                playerJpaEntity.getAchievements().stream().map(achievementJpaConverter::toDomain).collect(Collectors.toSet())
        );
    }
}
