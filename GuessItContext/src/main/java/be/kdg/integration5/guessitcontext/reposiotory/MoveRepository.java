package be.kdg.integration5.guessitcontext.reposiotory;

import be.kdg.integration5.guessitcontext.domain.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MoveRepository extends JpaRepository<Move, UUID> {
}
