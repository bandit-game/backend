package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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
    private Predictions predictions;

    public enum Gender {
        MALE, FEMALE
    }

    public Player(String playerName, PlayerId playerId, int age, Gender gender, Location location) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.metrics = new Metrics();
        this.predictions = new Predictions(0.0, 0.0, Predictions.PlayerClass.BEGINNER);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerId, player.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playerId);
    }
}
