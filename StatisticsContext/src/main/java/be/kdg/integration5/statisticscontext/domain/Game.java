package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Game {
    private GameId gameId;
    private String name;

}
