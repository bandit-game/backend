package be.kdg.integration5.checkerscontext.adapter.out.square;

import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.piece.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.domain.PlayedPosition;
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

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @OneToOne(optional = true)
    private PieceJpaEntity placedPiece;

    public static SquareJpaEntity of(Square square) {
        return new SquareJpaEntity(
                new SquareJpaEntityId(
                        square.getBoard().getGame().getPlayedMatchId().uuid(),
                        square.getSquareNumber()
                ),
                BoardJpaEntity.of(square.getBoard()),
                square.getPlayedPosition().x(),
                square.getPlayedPosition().y(),
                PieceJpaEntity.of(square.getPlacedPiece())
        );
    }

    public Square toDomain() {
        return new Square(
                this.getBoard().toDomain(),
                this.getSquareId().getSquareNumber(),
                new PlayedPosition(this.getX(), this.getY()),
                this.getPlacedPiece().toDomain()
        );
    }
}
