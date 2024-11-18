package be.kdg.integration5.gameplatformcontext.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {
}
