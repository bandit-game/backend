package be.kdg.integration5.checkerscontext.adapter.out.square;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface SquareJpaRepository extends JpaRepository<SquareJpaEntity, SquareJpaEntityId> {

    @Query("select s from SquareJpaEntity s " +
            "left join fetch s.placedPiece p " +
            "left join fetch p.square ps " +
            "left join fetch ps.board b " +
            "left join fetch b.game " +
            "where s.squareId.boardId = :gameId " +
            "and s.squareId.x = :x " +
            "and s.squareId.y = :y ")
    Optional<SquareJpaEntity> findByGameIdAndXAndYFetched(UUID gameId, int x, int y);
}
