package be.kdg.integration5.gameplatformcontext.adapter.out.lobby;

import org.apache.qpid.proton.codec.UUIDType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {

    @Query("select l from LobbyJpaEntity l " +
    "left join fetch l.game g " +
    "where g.gameId = :gameId and " +
    "size(l.players) < g.maxLobbyPlayerAmount and " +
    "l.isPrivate = :privacy")
    List<LobbyJpaEntity> findAllNotFilledNonPrivateLobbiesByGameIdCustom(UUID gameId, boolean privacy);
}
