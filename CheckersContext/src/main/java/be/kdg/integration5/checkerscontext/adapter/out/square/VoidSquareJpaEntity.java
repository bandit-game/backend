package be.kdg.integration5.checkerscontext.adapter.out.square;

import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Board;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VoidSquareJpaEntity extends SquareJpaEntity {

    public VoidSquareJpaEntity(BoardJpaEntity board, SquareJpaEntityId squareId) {
        super(squareId, board);
    }
}
