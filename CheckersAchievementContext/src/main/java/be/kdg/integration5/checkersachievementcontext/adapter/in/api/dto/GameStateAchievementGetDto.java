package be.kdg.integration5.checkersachievementcontext.adapter.in.api.dto;

public class GameStateAchievementGetDto extends AchievementGetDto {
    private static final String achievementTypeName = "GAME_STATE_ACHIEVEMENT";

    public GameStateAchievementGetDto(String name, String description, String imageUrl, Boolean isAchieved) {
        super(name, description, imageUrl, achievementTypeName, isAchieved);
    }
}
