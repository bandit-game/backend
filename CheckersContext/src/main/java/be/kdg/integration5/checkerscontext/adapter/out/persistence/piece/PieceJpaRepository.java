package be.kdg.integration5.checkerscontext.adapter.out.persistence.piece;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceJpaRepository extends JpaRepository<PieceJpaEntity, PieceJpaEntityId> {
}
