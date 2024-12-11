package be.kdg.integration5.checkerscontext.adapter.out.piece;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PieceJpaEntityId implements Serializable {
    private UUID gameId;
    private int currentX;
    private int currentY;
}
