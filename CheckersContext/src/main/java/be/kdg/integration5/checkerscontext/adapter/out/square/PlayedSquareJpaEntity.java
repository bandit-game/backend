package be.kdg.integration5.checkerscontext.adapter.out.square;

import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Board;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayedSquareJpaEntity extends SquareJpaEntity {
    @Column(nullable = false)
    private int playedX;

    @Column(nullable = false)
    private int playedY;

    @OneToOne(optional = true)
    private PieceJpaEntity placedPiece;

    public PlayedSquareJpaEntity(SquareJpaEntityId squareId, BoardJpaEntity board, int playedX, int playedY, PieceJpaEntity placedPiece) {
        super(squareId, board);
        this.playedX = playedX;
        this.playedY = playedY;
        this.placedPiece = placedPiece;
    }
}
