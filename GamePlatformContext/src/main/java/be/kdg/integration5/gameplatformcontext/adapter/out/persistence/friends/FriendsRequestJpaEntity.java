package be.kdg.integration5.gameplatformcontext.adapter.out.persistence.friends;

import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player.PlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.domain.FriendRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "friend_requests")
@Getter
@Setter
public class FriendsRequestJpaEntity {
    @Id
    private UUID requestId;

    @ManyToOne
    private PlayerJpaEntity sender;

    @ManyToOne
    private PlayerJpaEntity receiver;

    @Enumerated(EnumType.STRING)
    private FriendRequest.Status status;

    public FriendRequest toDomain() {
        return new FriendRequest(requestId, sender.toDomain(), receiver.toDomain(), status);
    }

    public static FriendsRequestJpaEntity of(FriendRequest request) {
        FriendsRequestJpaEntity entity = new FriendsRequestJpaEntity();
        entity.requestId = request.getRequestUUID();
        entity.sender = PlayerJpaEntity.of(request.getSender());
        entity.receiver = PlayerJpaEntity.of(request.getReceiver());
        entity.status = request.getStatus();
        return entity;
    }
}
