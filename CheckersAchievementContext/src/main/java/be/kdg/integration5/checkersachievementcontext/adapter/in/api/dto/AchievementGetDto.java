package be.kdg.integration5.checkersachievementcontext.adapter.in.api.dto;

import lombok.Getter;

@Getter
public abstract class AchievementGetDto {
    private final String name;
    private final String description;
    private final String imageUrl;
    private final String achievementType;
    private final Boolean isAchieved;

    public AchievementGetDto(String name, String description, String imageUrl, String achievementType, Boolean isAchieved) {
        if (name == null || description == null || imageUrl == null || achievementType == null || isAchieved == null)
            throw new NullPointerException();

        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.achievementType = achievementType;
        this.isAchieved = isAchieved;
    }
}
