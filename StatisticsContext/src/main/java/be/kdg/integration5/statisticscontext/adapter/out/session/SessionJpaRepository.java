package be.kdg.integration5.statisticscontext.adapter.out.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionJpaRepository extends JpaRepository<SessionJpaEntity, UUID> {
    @Query("select distinct s from SessionJpaEntity s " +
    "left join fetch s.game g " +
    "left join fetch s.winner w " +
    "left join fetch s.players ps " +
    "left join fetch ps.moves m " +
    "left join fetch ps.player p " +
    "left join fetch p.playerMetrics pm " +
    "left join fetch p.predictions pp " +
    "where s.sessionId = :sessionId")
    Optional<SessionJpaEntity> findBySessionIdFetched(UUID sessionId);
}
