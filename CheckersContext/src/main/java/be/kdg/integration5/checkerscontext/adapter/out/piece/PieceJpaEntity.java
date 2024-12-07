package be.kdg.integration5.checkerscontext.adapter.out.piece;

import be.kdg.integration5.checkerscontext.adapter.out.square.SquareJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Piece;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJpaEntity {
    @EmbeddedId
    private PieceJpaEntityId pieceId;

    @Column(nullable = false)
    private boolean isKing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Piece.PieceColor pieceColor;

    @OneToOne
    private SquareJpaEntity square;

    public static PieceJpaEntity of(Piece piece) {
        return new PieceJpaEntity(
                new PieceJpaEntityId(
                        piece.getSquare().getBoard().getGame().getPlayedMatchId().uuid(),
                        piece.getPieceNumber()
                ),
                piece.isKing(),
                piece.getColor(),
                SquareJpaEntity.of(piece.getSquare())
        );
    }

    public Piece toDomain() {
        return new Piece(
                this.pieceId.getPieceNumber(),
                this.square.toDomain(),
                this.isKing,
                this.pieceColor
        );
    }
}
