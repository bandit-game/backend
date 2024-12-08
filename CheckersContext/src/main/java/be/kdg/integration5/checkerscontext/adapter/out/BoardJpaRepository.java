package be.kdg.integration5.checkerscontext.adapter.out;

import be.kdg.integration5.checkerscontext.adapter.out.game.GameJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardJpaRepository extends JpaRepository<BoardJpaEntity, GameJpaEntity> {
}
