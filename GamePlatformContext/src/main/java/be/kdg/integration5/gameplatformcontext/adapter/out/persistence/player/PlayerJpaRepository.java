package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {

    List<PlayerJpaEntity> findAllByUsernameContainingIgnoreCase(String username);
}
