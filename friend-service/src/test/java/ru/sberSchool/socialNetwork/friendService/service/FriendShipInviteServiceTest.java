package ru.sberSchool.socialNetwork.friendService.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.sberSchool.socialNetwork.friendService.service.impl.FriendShipInviteServiceImpl;
import ru.sberSchool.socialNetwork.userService.model.User;
import ru.sberSchool.socialNetwork.userService.service.UserService;

import java.util.*;

@Slf4j
class FriendShipInviteServiceTest {

    private FriendshipInviteService friendshipService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        friendshipService = new FriendShipInviteServiceImpl(userService);
    }

    @Test
    void testAddFriend_positive() {
        UUID userId = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();

        User user = new User(userId, "User1", "user1@example.com");
        User friend = new User(friendId, "Friend1", "friend1@example.com");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(userService.getUserById(friendId)).thenReturn(Optional.of(friend));

        log.debug("testAddFriend_positive[0]: Testing addFriend with userId: {}, friendId: {}", userId, friendId);

        friendshipService.addFriend(userId, friendId);

        List<User> userFriends = friendshipService.getFriends(userId);
        List<User> friendFriends = friendshipService.getFriends(friendId);

        assertEquals(1, userFriends.size());
        assertEquals(1, friendFriends.size());
        assertTrue(userFriends.contains(friend));
        assertTrue(friendFriends.contains(user));

        log.debug("testAddFriend_positive[1]: addFriend executed successfully and users resolved: {} and {}", userFriends, friendFriends);
    }

    @Test
    void testRemoveFriend_positive() {
        UUID userId = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();

        log.debug("testRemoveFriend_positive[0]: Testing removeFriend after adding friend.");

        friendshipService.addFriend(userId, friendId);
        friendshipService.removeFriend(userId, friendId);

        List<User> userFriends = friendshipService.getFriends(userId);
        List<User> friendFriends = friendshipService.getFriends(friendId);

        assertTrue(userFriends.isEmpty());
        assertTrue(friendFriends.isEmpty());

        log.debug("testRemoveFriend_positive[1]: removeFriend executed successfully.");
    }

    @Test
    void testRemoveFriend_negative() {
        UUID userId = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();

        log.debug("testRemoveFriend_negative[0]: Testing removeFriend with non-existent friendship.");

        friendshipService.removeFriend(userId, friendId);

        List<User> userFriends = friendshipService.getFriends(userId);
        List<User> friendFriends = friendshipService.getFriends(friendId);

        assertTrue(userFriends.isEmpty());
        assertTrue(friendFriends.isEmpty());

        log.debug("testRemoveFriend_negative[1]: removeFriend handled non-existent friendship gracefully.");
    }

    @Test
    void testGetFriends_positive() {
        UUID userId = UUID.randomUUID();
        UUID friendId1 = UUID.randomUUID();
        UUID friendId2 = UUID.randomUUID();

        User friend1 = new User(friendId1, "Friend1", "friend1@example.com");
        User friend2 = new User(friendId2, "Friend2", "friend2@example.com");

        when(userService.getUserById(friendId1)).thenReturn(Optional.of(friend1));
        when(userService.getUserById(friendId2)).thenReturn(Optional.of(friend2));

        log.debug("testGetFriends_positive[0]: Testing getFriends with valid friendships.");

        friendshipService.addFriend(userId, friendId1);
        friendshipService.addFriend(userId, friendId2);

        List<User> friends = friendshipService.getFriends(userId);

        assertEquals(2, friends.size());
        assertTrue(friends.contains(friend1));
        assertTrue(friends.contains(friend2));

        log.debug("testGetFriends_positive[1]: Successfully retrieved friends: {}", friends);
    }

    @Test
    void testGetFriends_negative() {
        UUID userId = UUID.randomUUID();

        log.debug("testGetFriends_negative[0]: Testing getFriends with user having no friends.");

        List<User> friends = friendshipService.getFriends(userId);

        assertTrue(friends.isEmpty());

        log.debug("testGetFriends_negative[1]: No friends found for user: {}", userId);
    }
}
