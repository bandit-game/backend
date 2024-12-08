package be.kdg.integration5.checkerscontext.adapter.out.board;

import be.kdg.integration5.checkerscontext.adapter.out.game.GameJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardJpaEntity, GameJpaEntity> {
}
