package be.kdg.integration5.checkerscontext.adapter.out.board;

import be.kdg.integration5.checkerscontext.adapter.out.game.GameJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardJpaEntity, GameJpaEntity> {

    @Query("select b from BoardJpaEntity b " +
    "left join fetch b.currentPlayer p " +
    "left join fetch b.squares s ")
    Optional<BoardJpaEntity> findByGameIdFetched(UUID gameId);
}
