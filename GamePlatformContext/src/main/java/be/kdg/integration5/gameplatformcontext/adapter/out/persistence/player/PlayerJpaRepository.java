package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {
    List<PlayerJpaEntity> findAllByUsernameContainingIgnoreCase(String username);
    PlayerJpaEntity findByPlayerId(UUID playerId);
}
