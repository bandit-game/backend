package be.kdg.integration5.gameplatformcontext.core;

import be.kdg.integration5.gameplatformcontext.GamePlatformContextApplication;
import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.friends.FriendsRequestJpaRepository;
import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player.PlayerJpaEntity;
import be.kdg.integration5.gameplatformcontext.adapter.out.persistence.player.PlayerJpaRepository;
import be.kdg.integration5.gameplatformcontext.domain.FriendRequest;
import be.kdg.integration5.gameplatformcontext.domain.Player;
import be.kdg.integration5.gameplatformcontext.domain.PlayerId;
import be.kdg.integration5.gameplatformcontext.port.in.FriendsMatchingUseCase;
import be.kdg.integration5.gameplatformcontext.port.out.FriendsRequestPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ActiveProfiles("test")
@ContextConfiguration(classes = { GamePlatformContextApplication.class })
@SpringBootTest
@AutoConfigureMockMvc
public class FriendsMatchingUseCaseImplTest {
    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    private FriendsRequestJpaRepository requestJpaRepository;

    @Autowired
    private FriendsMatchingUseCase friendsMatchingUseCase;

    @Autowired
    private FriendsRequestPort friendRequestPort;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FriendsRequestJpaRepository friendsRequestJpaRepository;

    private UUID senderId;
    private UUID receiverId;

    @BeforeEach
    void setup() {
        senderId = UUID.randomUUID();
        receiverId = UUID.randomUUID();

        playerJpaRepository.save(new PlayerJpaEntity(senderId, 25, Player.Gender.MALE, "bob"));
        playerJpaRepository.save(new PlayerJpaEntity(receiverId, 30, Player.Gender.FEMALE, "lisa"));
    }

    @AfterEach
    void tearDown(){
        friendsRequestJpaRepository.deleteAll();
        playerJpaRepository.deleteAll();
    }

    // Send a friend request
    @Test
    @Transactional
    @WithMockUser(roles = "player")
    void testSendFriendRequest() throws Exception {
        // Act
        mockMvc.perform(post("/api/v1/friends/send")
                        .param("senderId", senderId.toString())
                        .param("receiverId", receiverId.toString()))
                .andExpect(status().isOk());

        // Assert
        List<FriendRequest> requests = friendsMatchingUseCase.getPendingFriendRequests(new PlayerId(receiverId));
        assertThat(requests, hasSize(1));
        assertThat(requests.get(0).getSender().getUsername(), is("bob"));
        assertThat(requests.get(0).getReceiver().getUsername(), is("lisa"));
        assertThat(requests.get(0).getStatus(), is(FriendRequest.Status.PENDING));
    }

    // Accept a friend request
    @Test
    @Transactional
    @WithMockUser(roles = "player")
    void testAcceptFriendRequest() throws Exception {
        // Arrange - Create a friend request
        friendsMatchingUseCase.sendFriendsRequest(new PlayerId(senderId), new PlayerId(receiverId));
        FriendRequest request = friendsMatchingUseCase.getPendingFriendRequests(new PlayerId(receiverId)).get(0);

        // Act - Accept the request
        mockMvc.perform(post("/api/v1/friends/respond")
                        .param("requestId", request.getRequestUUID().toString())
                        .param("accepted", "true"))
                .andExpect(status().isOk());

        // Assert
        Player sender = playerJpaRepository.findByPlayerIdFetchedFriends(senderId).get().toDomain(); // Reload entity
        Player receiver = playerJpaRepository.findByPlayerIdFetchedFriends(receiverId).get().toDomain(); // Reload entity

        assertThat(sender.getFriends(), hasSize(1));
        assertThat(receiver.getFriends(), hasSize(1));
        assertThat(sender.getFriends(), hasItem(hasProperty("username", is("lisa"))));
        assertThat(receiver.getFriends(), hasItem(hasProperty("username", is("bob"))));
    }

    // Test 3: Decline a friend request
    @Test
    @Transactional
    @WithMockUser(roles = "player")
    void testDeclineFriendRequest() throws Exception {
        // Arrange - Create a friend request
        friendsMatchingUseCase.sendFriendsRequest(new PlayerId(senderId), new PlayerId(receiverId));
        FriendRequest request = friendsMatchingUseCase.getPendingFriendRequests(new PlayerId(receiverId)).get(0);

        // Act - Decline the request
        mockMvc.perform(post("/api/v1/friends/respond")
                        .param("requestId", request.getRequestUUID().toString())
                        .param("accepted", "false"))
                .andExpect(status().isOk());

        // Assert - Check players' friends list
        Player sender = playerJpaRepository.findByPlayerIdFetchedFriends(senderId).get().toDomain();
        Player receiver = playerJpaRepository.findByPlayerIdFetchedFriends(receiverId).get().toDomain();
        assertThat(sender.getFriends(), hasSize(0));
        assertThat(receiver.getFriends(), hasSize(0));

        // Assert - Check the updated request status
        FriendRequest updatedRequest = friendRequestPort.findFriendRequestById(request.getRequestUUID());
        assertThat(updatedRequest, is(notNullValue())); // Ensure the request exists
        assertThat(updatedRequest.getStatus(), is(FriendRequest.Status.DECLINED)); // Verify the status is DECLINED
    }


    // Test 4: Get pending requests
    @Test
    @Transactional
    @WithMockUser(roles = "player")
    void testGetPendingFriendRequests() throws Exception {
        // Arrange - Create two friend requests
        friendsMatchingUseCase.sendFriendsRequest(new PlayerId(senderId), new PlayerId(receiverId));

        // Act and Assert
        mockMvc.perform(get("/api/v1/friends/pending")
                        .param("playerId", receiverId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sender.username", is("bob")));
    }

    // Test 5: Get friends list
    @Test
    @Transactional
    @WithMockUser(roles = "player")
    void testGetFriendsList() throws Exception {
        // Arrange - Create a friend request and accept it
        friendsMatchingUseCase.sendFriendsRequest(new PlayerId(senderId), new PlayerId(receiverId));
        FriendRequest request = friendsMatchingUseCase.getPendingFriendRequests(new PlayerId(receiverId)).get(0);
        friendsMatchingUseCase.respondToFriendRequest(request.getRequestUUID(), true);

        // Act and Assert - Fetch friends list
        mockMvc.perform(get("/api/v1/friends")
                        .param("playerId", senderId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is(receiverId.toString())));
    }
}
