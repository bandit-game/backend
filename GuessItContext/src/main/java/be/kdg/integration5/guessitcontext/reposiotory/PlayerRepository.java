package be.kdg.integration5.guessitcontext.reposiotory;

import be.kdg.integration5.guessitcontext.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
