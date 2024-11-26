package be.kdg.integration5.checkerscontext.adapter.out;

import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.square.SquareJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Board;
import be.kdg.integration5.checkerscontext.domain.GameId;
import be.kdg.integration5.checkerscontext.domain.Square;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardJpaEntity {
    @Id
    private UUID boardId;

    @ManyToOne
    private PlayerJpaEntity currentPlayer;

    @OneToMany
    private List<SquareJpaEntity> squares;

    public static BoardJpaEntity of(Board board, GameId gameId) {
        List<SquareJpaEntity> squareJpaEntities = new ArrayList<>();

        Arrays.stream(board.getSquares()).forEach(
                squaresRow -> Arrays.stream(squaresRow).forEach(
                        square -> squareJpaEntities.add(SquareJpaEntity.of(square))
                )
        );

        return new BoardJpaEntity(
                gameId.uuid(),
                PlayerJpaEntity.of(board.getCurrentPlayer()),
                squareJpaEntities
        );
    }

    public Board toDomain() {
        Square[][] squaresArray = new Square[Board.BOARD_SIZE][Board.BOARD_SIZE];

        for (int i = 0; i < Board.BOARD_SIZE; i++)
            for (int j = 0; j < Board.BOARD_SIZE; j++)
                squaresArray[i][j] = this.squares.get(i * 10 + j).toDomain();

        return new Board(
                squaresArray,
                this.currentPlayer.toDomain()
        );
    }
}
