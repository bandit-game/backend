package be.kdg.integration5.checkerscontext.adapter.out.square;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface SquareJpaRepository extends JpaRepository<SquareJpaEntity, SquareJpaEntityId> {

    @Query("select s from SquareJpaEntity s " +
            "join fetch s.placedPiece p " +
            "join fetch p.square ps " +
            "join fetch ps.board b " +
            "join fetch b.game " +
            "where s.squareId.boardId = ?1 " +
            "and s.squareId.x = ?2 " +
            "and s.squareId.y = ?3 ")
    Optional<SquareJpaEntity> findByGameIdAndXAndYFetched(UUID gameId, int x, int y);
}
