package be.kdg.integration5.checkerscontext.adapter.out.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {
}
