package be.kdg.integration5.checkerscontext.repository;

import be.kdg.integration5.checkerscontext.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
}
