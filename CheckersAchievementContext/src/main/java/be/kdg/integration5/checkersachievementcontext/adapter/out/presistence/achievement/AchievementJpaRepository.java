package be.kdg.integration5.checkersachievementcontext.adapter.out.presistence.achievement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementJpaRepository extends JpaRepository<AchievementJpaEntity, String> {
}
