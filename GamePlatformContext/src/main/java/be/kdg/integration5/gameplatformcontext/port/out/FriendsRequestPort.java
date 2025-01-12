package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.FriendRequest;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FriendsRequestPort {
    void saveFriends(FriendRequest friendRequest);
    FriendRequest findFriendRequestById(UUID requestId);
    void updateFriendRequest(FriendRequest friendRequest);
    List<FriendRequest> findPendingRequestsByReceiverFetched(PlayerId receiverId);
    FriendRequest findReceiverById(PlayerId receiverId);
}
