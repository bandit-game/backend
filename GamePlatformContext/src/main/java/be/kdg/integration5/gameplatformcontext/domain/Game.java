package be.kdg.integration5.gameplatformcontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Game {
    private GameId gameId;
    private String title;
    private String description;
    private Price price;
    private List<Achievement> achievements;
}
