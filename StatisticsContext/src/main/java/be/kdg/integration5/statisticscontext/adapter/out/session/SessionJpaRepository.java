package be.kdg.integration5.statisticscontext.adapter.out.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionJpaRepository extends JpaRepository<SessionJpaEntity, UUID> {
    @Query("select s from SessionJpaEntity s " +
    "left join s.game g " +
    "left join s.winner w " +
    "left join s.players ps " +
    "left join ps.player p " +
    "where s.sessionId = :sessionId")
    Optional<SessionJpaEntity> findBySessionIdFetched(UUID sessionId);
}
