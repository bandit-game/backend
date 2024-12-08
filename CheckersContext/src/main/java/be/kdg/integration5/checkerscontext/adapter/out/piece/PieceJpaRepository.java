package be.kdg.integration5.checkerscontext.adapter.out.piece;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PieceJpaRepository extends JpaRepository<PieceJpaEntity, PieceJpaEntityId> {

//    @Query("select p from PieceJpaEntity p join fetch p.")
//    Optional<PieceJpaEntity> findByIdWithRelatedEntitiesFetched(PieceJpaEntityId id);
}
