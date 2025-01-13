package be.kdg.integration5.guessitcontext.controller.api.dto;

import java.util.Objects;
import java.util.UUID;

public record AchievementGetDto(UUID achievementId, String name, String description, String imageUrl, Boolean isAchieved) {
    public AchievementGetDto {
        Objects.requireNonNull(achievementId);
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(isAchieved);
    }
}
