package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.domain.FriendRequest;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.in.FriendsMatchingUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.FindPlayerPort;
import be.kdg.integration5.gameplatformcontext.port.out.FriendsRequestPort;
import be.kdg.integration5.gameplatformcontext.port.out.PersistPlayerPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FriendsMatchingUseCaseImpl implements FriendsMatchingUseCase {
    private final FriendsRequestPort friendRequestPort;
    private final FindPlayerPort findPlayerPort;
    private final PersistPlayerPort persistPlayerPort;

    public FriendsMatchingUseCaseImpl(FriendsRequestPort friendRequestPort, FindPlayerPort findPlayerPort, PersistPlayerPort persistPlayerPort) {
        this.friendRequestPort = friendRequestPort;
        this.findPlayerPort = findPlayerPort;
        this.persistPlayerPort = persistPlayerPort;
    }


    @Override
    public void sendFriendsRequest(PlayerId senderId, PlayerId receiverId) {
        Player sender = findPlayerPort.findPlayerById(senderId);
        Player receiver = findPlayerPort.findPlayerById(receiverId);

        if (!findPlayerPort.playerExists(receiverId)) {
            throw new IllegalArgumentException("Player could not be found to send friends request");
        }else if (sender.isFriendsWithPlayer(receiver)) {
            throw new IllegalArgumentException("Already friends!");
        }else{
            FriendRequest friendsRequest = new FriendRequest(UUID.randomUUID(), sender, receiver, FriendRequest.Status.PENDING);
            friendRequestPort.saveFriends(friendsRequest);
        }
    }

//    @Override
//    public void receiveFriendsRequest(PlayerId sender, PlayerId receiver, boolean acceptedStatus) {
//        FriendRequest request = friendRequestPort.findFriendRequestById(sender.uuid());
//
//    }

    @Override
    public void respondToFriendRequest(UUID requestId, boolean accepted) {
        FriendRequest request = friendRequestPort.findFriendRequestById(requestId);
        if (accepted) {
            request.setStatus(FriendRequest.Status.ACCEPTED);

            // Add friends
            Player sender = request.getSender();
            Player receiver = request.getReceiver();
            sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);

            // Persist changes explicitly
            persistPlayerPort.save(sender);
            persistPlayerPort.save(receiver);
            friendRequestPort.saveFriends(request); // Ensure request is saved
        } else {
            request.setStatus(FriendRequest.Status.DECLINED);
            friendRequestPort.saveFriends(request); // Ensure decline status is persisted
        }
    }


    @Override
    public List<FriendRequest> getPendingFriendRequests(PlayerId playerId) {
        return friendRequestPort.findPendingRequestsByReceiver(playerId);
    }

    @Override
    public List<PlayerId> getFriends(PlayerId playerId) {
        return findPlayerPort.findPlayerById(playerId)
                .getFriends()
                .stream()
                .map(Player::getPlayerId)
                .toList();
    }
}
