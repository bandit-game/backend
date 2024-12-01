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
    private int positionNumber;

    @OneToOne(optional = true)
    private PieceJpaEntity placedPiece;

    public PlayedSquareJpaEntity(SquareJpaEntityId squareId, BoardJpaEntity board, int positionNumber, PieceJpaEntity placedPiece) {
        super(squareId, board);
        this.positionNumber = positionNumber;
        this.placedPiece = placedPiece;
    }
}
