package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import be.kdg.integration5.gameplatformcontext.adapter.out.player.PlayerJpaEntity;
import jakarta.transaction.Transactional;
import org.apache.qpid.proton.codec.UUIDType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {

    @Query("select l from LobbyJpaEntity l " +
    "left join fetch l.game g " +
    "left join fetch l.lobbyPlayers lp " +
    "left join fetch lp.player p " +
    "where g.gameId = :gameId and " +
    "size(lp) < g.maxLobbyPlayerAmount and " +
    "l.isPrivate = :privacy")
    List<LobbyJpaEntity> findAllNotFilledNonPrivateLobbiesByGameIdCustom(UUID gameId, boolean privacy);

    @Query("select l from LobbyJpaEntity l " +
    "left join fetch l.game g " +
    "left join fetch l.lobbyPlayers lp " +
    "left join fetch lp.player p " +
    "where l.lobbyId in ( " +
            "        select lInner.lobbyId from LobbyJpaEntity lInner " +
            "        join lInner.lobbyPlayers lpInner " +
            "        join lpInner.player pInner " +
            "        where pInner.playerId = :playerId " +
            "    ) and l.isReady = :isReady")
    Optional<LobbyJpaEntity> findByPlayerIdAndIsReadyCustom(UUID playerId, boolean isReady);


}
