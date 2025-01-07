package be.kdg.integration5.checkersachievementcontext.domain.achievement;

public abstract class GameStateAchievement extends Achievement {

    public GameStateAchievement(String name, String description, String imagUrl) {
        super(name, description, imagUrl);
    }

    public GameStateAchievement(String name, String description, String imagUrl, boolean isAchieved) {
        super(name, description, imagUrl, isAchieved);
    }
}
