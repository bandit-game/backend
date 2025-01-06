package be.kdg.integration5.checkersachievementcontext.domain;

import be.kdg.integration5.checkersachievementcontext.domain.achievement.Achievement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private PlayerId playerId;

    private List<Achievement> achievements;
}
