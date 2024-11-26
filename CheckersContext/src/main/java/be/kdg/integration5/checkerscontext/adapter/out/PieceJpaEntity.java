package be.kdg.integration5.checkerscontext.adapter.out;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
}
