package be.kdg.integration5.checkerscontext.adapter.out.square;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SquareJpaEntityId implements Serializable {
    private UUID boardId;
    private int x;
    private int y;
}
