package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.lobby_player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LobbyPlayerJpaRepository extends JpaRepository<LobbyPlayerJpaEntity, LobbyPlayerId> {
    @Query("select lp from LobbyPlayerJpaEntity lp " +
    "left join fetch lp.player p " +
    "left join fetch lp.lobby l " +
    "where l.lobbyId = :lobbyId")
    List<LobbyPlayerJpaEntity> findByLobbyIdFetched(UUID lobbyId);
}
