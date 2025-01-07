package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {

    @Query("select g from GameJpaEntity g " +
            "join fetch g.players " +
            "join fetch g.moves m " +
            "join fetch m.mover " +
            "where g.gameId = :id")
    Optional<GameJpaEntity> findByIdFetched(UUID id);
}
