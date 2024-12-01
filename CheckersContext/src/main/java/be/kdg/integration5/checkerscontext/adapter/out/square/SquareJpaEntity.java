package be.kdg.integration5.checkerscontext.adapter.out.square;

import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.domain.PlayableSquare;
import be.kdg.integration5.checkerscontext.domain.PlayedPosition;
import be.kdg.integration5.checkerscontext.domain.Square;
import be.kdg.integration5.checkerscontext.domain.VoidSquare;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "squares")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class SquareJpaEntity {
    @EmbeddedId
    private SquareJpaEntityId squareId;

    @ManyToOne
    private BoardJpaEntity board;

    public static SquareJpaEntity of(Square square) {
        if (square instanceof PlayableSquare playableSquare)
            return new PlayedSquareJpaEntity(
                    new SquareJpaEntityId(
                            square.getBoard().getGame().getPlayedMatchId().uuid(),
                            playableSquare.getSquareNumber()
                    ),
                    BoardJpaEntity.of(playableSquare.getBoard()),
                    playableSquare.getPlayedPosition().playedSquareNumber(),
                    PieceJpaEntity.of(playableSquare.getPlacedPiece())
            );
        else if (square instanceof VoidSquare voidSquare)
            return new VoidSquareJpaEntity(
                    BoardJpaEntity.of(voidSquare.getBoard()),
                    new SquareJpaEntityId(
                            square.getBoard().getGame().getPlayedMatchId().uuid(),
                            voidSquare.getSquareNumber()
                    )
            );
        else
            return null;
    }

    public Square toDomain() {
        if (this instanceof PlayedSquareJpaEntity playedSquareJpaEntity)
            return new PlayableSquare(
                    playedSquareJpaEntity.getBoard().toDomain(),
                    playedSquareJpaEntity.getSquareId().getSquareNumber(),
                    new PlayedPosition(playedSquareJpaEntity.getPositionNumber())
            );

        else if (this instanceof VoidSquareJpaEntity voidSquareJpaEntity)
            return new VoidSquare(
                    voidSquareJpaEntity.getBoard().toDomain(),
                    voidSquareJpaEntity.getSquareId().getSquareNumber()
            );
        else
            return null;
    }
}
