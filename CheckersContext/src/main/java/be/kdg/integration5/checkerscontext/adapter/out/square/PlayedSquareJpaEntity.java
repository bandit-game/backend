package be.kdg.integration5.checkerscontext.adapter.out.square;

import be.kdg.integration5.checkerscontext.adapter.out.PieceJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PlayedSquareJpaEntity extends SquareJpaEntity {
    @Column(nullable = false)
    private int positionNumber;

    @OneToOne(optional = true)
    private PieceJpaEntity placedPiece;
}
