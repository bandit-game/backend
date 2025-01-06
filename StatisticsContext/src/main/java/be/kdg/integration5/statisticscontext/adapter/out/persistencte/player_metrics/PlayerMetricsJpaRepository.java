package be.kdg.integration5.statisticscontext.adapter.out.persistencte.player_metrics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerMetricsJpaRepository extends JpaRepository<PlayerMetricsJpaEntity, UUID> {
}
