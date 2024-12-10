package be.kdg.integration5.checkerscontext.adapter.out.piece;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PieceJpaRepository extends JpaRepository<PieceJpaEntity, PieceJpaEntityId> {
    List<PieceJpaEntity> findAllByPieceId_GameId(UUID gameId);
}
