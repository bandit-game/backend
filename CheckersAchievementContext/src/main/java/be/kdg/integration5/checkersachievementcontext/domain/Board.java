package be.kdg.integration5.checkersachievementcontext.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Board {

    private List<Move> movesHistory;
}
