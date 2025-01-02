package be.kdg.integration5.statisticscontext.adapter.out.player_session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerSessionJpaRepository extends JpaRepository<PlayerSessionJpaEntity, PlayerSessionId> {
}
