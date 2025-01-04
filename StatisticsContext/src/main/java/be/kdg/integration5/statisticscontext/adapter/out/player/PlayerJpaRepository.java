package be.kdg.integration5.statisticscontext.adapter.out.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {

    @Query("select p from PlayerJpaEntity p " +
    "left join fetch p.playerMetrics m " +
    "where p.playerId in :uuids")
    List<PlayerJpaEntity> findAllByIdsFetched(List<UUID> uuids);
}
