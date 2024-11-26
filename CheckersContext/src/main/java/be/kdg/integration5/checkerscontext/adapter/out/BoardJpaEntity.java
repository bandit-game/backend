package be.kdg.integration5.checkerscontext.adapter.out;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardJpaEntity {
    @Id
    private UUID boardId;

    @OneToMany
    private List<SquareJpaEntity> squares;


}
