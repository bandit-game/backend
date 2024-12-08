package be.kdg.integration5.checkerscontext.adapter.out.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {
}
