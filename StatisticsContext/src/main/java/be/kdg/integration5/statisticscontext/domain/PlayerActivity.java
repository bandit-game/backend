package be.kdg.integration5.statisticscontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlayerActivity {
    private Player player;
    private List<Move> moves;
}
