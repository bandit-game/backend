package be.kdg.integration5.checkerscontext.adapter.out.game;

import be.kdg.integration5.checkerscontext.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {


    @Query("select g from GameJpaEntity g " +
            "left join fetch g.players " +
            "left join fetch g.pieces " +
            "left join fetch g.currentPlayer ")
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
