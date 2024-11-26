package be.kdg.integration5.gameplatformcontext.adapter.out.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {
}
