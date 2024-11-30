package be.kdg.integration5.gameplatformcontext.adapter.out.lobby_player;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LobbyPlayerJpaRepository extends JpaRepository<LobbyPlayerJpaEntity, LobbyPlayerId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM LobbyPlayerJpaEntity lp WHERE lp.lobby.lobbyId = :lobbyId")
    void deleteByLobbyId(@Param("lobbyId") UUID lobbyId);

    @Query("select lp from LobbyPlayerJpaEntity lp " +
    "left join fetch lp.player p " +
    "left join fetch lp.lobby l " +
    "where l.lobbyId = :lobbyId")
    List<LobbyPlayerJpaEntity> findByLobbyIdFetched(UUID lobbyId);
}
