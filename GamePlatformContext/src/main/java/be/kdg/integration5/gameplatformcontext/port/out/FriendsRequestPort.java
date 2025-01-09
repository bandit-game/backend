package be.kdg.integration5.gameplatformcontext.port.out;

import be.kdg.integration5.gameplatformcontext.domain.FriendRequest;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;

import java.util.List;
import java.util.UUID;

public interface FriendsRequestPort {
    void saveFriends(FriendRequest friendRequest);
    FriendRequest findFriendRequestById(UUID requestId);
    void updateFriendRequest(FriendRequest friendRequest);
    List<FriendRequest> findPendingRequestsByReceiver(PlayerId receiverId);
//    List<PlayerId> findFriendsByPlayer(PlayerId playerId);
//    FriendRequest findSenderById(PlayerId senderId);
    FriendRequest findReceiverById(PlayerId receiverId);
}
