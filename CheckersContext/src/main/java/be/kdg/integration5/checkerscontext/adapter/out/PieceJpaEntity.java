package be.kdg.integration5.checkerscontext.adapter.out;

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

    public static PieceJpaEntity of(Piece piece) {
        return new PieceJpaEntity(
                new PieceJpaEntityId(
                        piece.getBoard().getGame().getPlayedMatchId().uuid(),
                        piece.getPieceNumber()
                ),
                piece.isKing(),
                piece.getColor()
        );
    }
}
