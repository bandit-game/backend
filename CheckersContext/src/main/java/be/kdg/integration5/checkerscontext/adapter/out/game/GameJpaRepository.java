package be.kdg.integration5.checkerscontext.adapter.out.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {


    @Query("select g from GameJpaEntity g " +
    "left join fetch g.players " +
    "left join fetch g.board b " +
    "left join fetch b.currentPlayer c")
    Optional<GameJpaEntity> findByIdFetched(UUID id);
}
