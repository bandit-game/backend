package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
//@EqualsAndHashCode(exclude = "name")
public class Player {
    private PlayerId playerId;
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
