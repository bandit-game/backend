package be.kdg.integration5.statisticscontext.adapter.out.prediction.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PredictionsJpaRepository extends JpaRepository<PredictionsJpaEntity, UUID> {
}
