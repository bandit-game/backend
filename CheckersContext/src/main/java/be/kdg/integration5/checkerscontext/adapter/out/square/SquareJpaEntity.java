package be.kdg.integration5.checkerscontext.adapter.out.square;

import be.kdg.integration5.checkerscontext.adapter.out.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.domain.PlayableSquare;
import be.kdg.integration5.checkerscontext.domain.Square;
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

    public static SquareJpaEntity of(Square square) {
        if (square instanceof PlayableSquare playableSquare)
            return new PlayedSquareJpaEntity(
                    playableSquare.getPosition().squareNumber(),
                    PieceJpaEntity.of(playableSquare.getPlacedPiece())
            )
    }

    public Square toDomain() {
        return ;
    }
}
