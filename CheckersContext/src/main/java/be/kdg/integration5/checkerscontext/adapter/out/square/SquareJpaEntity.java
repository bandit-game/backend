package be.kdg.integration5.checkerscontext.adapter.out.square;

import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.piece.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Square;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "squares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SquareJpaEntity {
    @EmbeddedId
    private SquareJpaEntityId squareId;

    @ManyToOne
    private BoardJpaEntity board;

    @OneToOne(optional = true)
    private PieceJpaEntity placedPiece;

    public static SquareJpaEntity of(Square square) {
        return new SquareJpaEntity(
                new SquareJpaEntityId(
                        square.getBoard().getGame().getPlayedMatchId().uuid(),
                        square.getX(),
                        square.getY()
                ),
                BoardJpaEntity.of(square.getBoard()),
                PieceJpaEntity.of(square.getPlacedPiece())
        );
    }

    public Square toDomain() {
        return new Square(
                this.getBoard().toDomain(),
                this.getSquareId().getX(),
                this.getSquareId().getY()
//                this.getPlacedPiece().toDomain()
        );
    }
}
