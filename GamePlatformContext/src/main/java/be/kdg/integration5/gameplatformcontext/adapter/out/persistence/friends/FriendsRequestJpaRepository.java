package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.friends;

import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player.PlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FriendsRequestJpaRepository extends JpaRepository<FriendsRequestJpaEntity, UUID> {
    List<FriendsRequestJpaEntity> findByReceiver_PlayerIdAndStatus(UUID receiverId, FriendRequest.Status status);
    FriendsRequestJpaEntity findByReceiver_PlayerId(UUID receiverId);
}
