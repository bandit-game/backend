package be.kdg.integration5.guessitcontext.reposiotory;

import be.kdg.integration5.guessitcontext.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Query("select distinct s from Session s " +
            "left join fetch s.players p " +
            "left join fetch s.firstPlayer fp " +
            "left join fetch s.currentPlayer cp " +
            "where s.isFinished = :isFinished and :playerId in (select sp.playerId from s.players sp)")
    Optional<Session> findByFinishedAndPlayerCustom(boolean isFinished, UUID playerId);
}
