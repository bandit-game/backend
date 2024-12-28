package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private PlayerId playerId;
    private String playerName;
    private int age;
    private Gender gender;
    private Location location;
    private Metrics metrics;


    public enum Gender {
        MALE, FEMALE
    }
}
