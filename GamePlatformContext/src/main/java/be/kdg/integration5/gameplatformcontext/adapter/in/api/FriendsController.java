package be.kdg.integration5.gameplatformcontext.adapter.in.api;

import be.kdg.integration5.gameplatformcontext.domain.FriendRequest;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.in.FriendsMatchingUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendsController {
    private final FriendsMatchingUseCase friendsMatchingUseCase;

    public FriendsController(FriendsMatchingUseCase friendsMatchingUseCase) {
        this.friendsMatchingUseCase = friendsMatchingUseCase;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendFriendRequest(@RequestParam UUID senderId, @RequestParam UUID receiverId) {
        friendsMatchingUseCase.sendFriendsRequest(new PlayerId(senderId), new PlayerId(receiverId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/respond")
    public ResponseEntity<?> respondToFriendRequest(@RequestParam UUID requestId, @RequestParam boolean accepted) {
        friendsMatchingUseCase.respondToFriendRequest(requestId, accepted);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FriendRequest>> getPendingRequests(@RequestParam UUID playerId) {
        List<FriendRequest> requests = friendsMatchingUseCase.getPendingFriendRequests(new PlayerId(playerId));
        return ResponseEntity.ok(requests);
    }

    @GetMapping
    public ResponseEntity<List<PlayerId>> getFriends(@RequestParam UUID playerId) {
        List<PlayerId> friends = friendsMatchingUseCase.getFriends(new PlayerId(playerId));
        return ResponseEntity.ok(friends);
    }
}
