package be.kdg.integration5.checkerscontext.adapter.out.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {

    @Query("select distinct g from GameJpaEntity g " +
            "left join fetch g.players " +
            "left join fetch g.pieces p " +
            "left join fetch p.owner " +
            "left join fetch g.currentPlayer " +
            "where g.gameId = :id")
    Optional<GameJpaEntity> findByIdFetched(UUID id);


    @Query("select g from GameJpaEntity g " +
            "left join fetch g.players p " +
            "where g.isFinished = false " +
            "and exists (" +
            " select pSub from g.players pSub where pSub.playerId = :playerId" +
            ")")
    Optional<GameJpaEntity> findByPlayerIdAndEndDateNullFetched(
            UUID playerId);
}
