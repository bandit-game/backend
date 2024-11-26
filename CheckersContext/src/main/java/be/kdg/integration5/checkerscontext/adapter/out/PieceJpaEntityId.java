package be.kdg.integration5.checkerscontext.adapter.out;

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
    private UUID boardId;
    private int pieceNumber;
}
