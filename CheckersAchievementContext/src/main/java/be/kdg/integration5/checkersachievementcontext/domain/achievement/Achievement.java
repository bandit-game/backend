package be.kdg.integration5.checkersachievementcontext.domain.achievement;

import be.kdg.integration5.checkersachievementcontext.domain.Game;
import be.kdg.integration5.checkersachievementcontext.domain.PlayerId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Achievement {
    private final String name;
    private String description;
    private String imagUrl;

    @Setter(AccessLevel.NONE)
    private boolean isAchieved;

    public Achievement(String name, String description, String imagUrl) {
        this.name = name;
        this.description = description;
        this.imagUrl = imagUrl;
    }

    public Achievement(String name, String description, String imagUrl, boolean isAchieved) {
        this.name = name;
        this.description = description;
        this.imagUrl = imagUrl;
        this.isAchieved = isAchieved;
    }

    public void open() {
        this.isAchieved = true;
    }

    public abstract boolean isFulfilled(Game game, PlayerId playerId);

}
