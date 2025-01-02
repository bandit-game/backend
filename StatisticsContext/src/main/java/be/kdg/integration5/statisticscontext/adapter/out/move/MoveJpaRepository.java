package be.kdg.integration5.statisticscontext.adapter.out.move;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MoveJpaRepository extends JpaRepository<MoveJpaEntity, UUID> {
}
