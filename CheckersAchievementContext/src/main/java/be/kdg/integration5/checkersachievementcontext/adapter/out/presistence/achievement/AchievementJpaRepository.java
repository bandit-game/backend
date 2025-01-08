package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface AchievementJpaRepository extends JpaRepository<AchievementJpaEntity, String> {
}
