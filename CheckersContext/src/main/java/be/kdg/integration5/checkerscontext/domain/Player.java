package be.kdg.integration5.checkerscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private PlayerId playerId;
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
