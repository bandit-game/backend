package be.kdg.integration5.gameplatformcontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Achievement {
    private AchievementId achievementId;
    private String name;
    private String description;
    private Image image;
}
