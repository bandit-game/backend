package be.kdg.integration5.gameplatformcontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private PlayerId playerId;
    private String username;
    private int age;
    private Gender gender;
    private List<Role> roles;
    private List<Achievement> earnedAchievements;

    public enum Gender {
        MALE, FEMALE
    }

    public enum Role {
        PLAYER, ADMIN
    }
}
