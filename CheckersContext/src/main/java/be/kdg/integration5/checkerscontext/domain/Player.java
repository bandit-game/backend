package be.kdg.integration5.checkerscontext.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "name")
public class Player {
    private PlayerId playerId;
    private String name;

    @Getter(AccessLevel.NONE)
    private boolean wantsDraw;

    public Player(String name) {
        this.name = name;
    }

    public boolean doesWantToDraw() {
        return wantsDraw;
    }
}
