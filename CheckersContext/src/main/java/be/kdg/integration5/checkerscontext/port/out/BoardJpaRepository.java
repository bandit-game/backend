package be.kdg.integration5.checkerscontext.port.out;

import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardJpaRepository extends JpaRepository<BoardJpaEntity, UUID> {
}
