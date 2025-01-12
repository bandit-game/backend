package be.kdg.integration5.gameplatformcontext.port.in;


import be.kdg.integration5.gameplatformcontext.domain.FriendRequest;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FriendsMatchingUseCase {
    void sendFriendsRequest (PlayerId sender, PlayerId receiver);
//    void receiveFriendsRequest (PlayerId sender, PlayerId receiver, boolean acceptedStatus);
    void respondToFriendRequest(UUID requestId, boolean accepted);
    List<FriendRequest> getPendingFriendRequests(PlayerId playerId);
    List<PlayerId> getFriends(PlayerId playerId);
}
