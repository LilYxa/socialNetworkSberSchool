package ru.sberSchool.socialNetwork.friendService.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.sberSchool.socialNetwork.friendService.service.FriendshipInviteService;
import ru.sberSchool.socialNetwork.userService.model.User;
import ru.sberSchool.socialNetwork.userService.service.UserService;

import java.util.*;

@Slf4j
public class FriendShipInviteServiceImpl implements FriendshipInviteService {

    private final Map<UUID, Set<UUID>> friendships = new HashMap<>();
    private final UserService userService;

    public FriendShipInviteServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addFriend(UUID userId, UUID friendId) {
        friendships.computeIfAbsent(userId, k -> new HashSet<>()).add(friendId);
        friendships.computeIfAbsent(friendId, k -> new HashSet<>()).add(userId);

        log.debug("Added friendship: {} <-> {}", userId, friendId);
    }

    @Override
    public void removeFriend(UUID userId, UUID friendId) {
        Optional.ofNullable(friendships.get(userId)).ifPresent(friends -> friends.remove(friendId));
        Optional.ofNullable(friendships.get(friendId)).ifPresent(friends -> friends.remove(userId));

        log.debug("Removed friendship: {} <-> {}", userId, friendId);
    }

    @Override
    public List<User> getFriends(UUID userId) {
        Set<UUID> friendIds = friendships.getOrDefault(userId, Collections.emptySet());
        List<User> friends = new ArrayList<>();
        for (UUID friendId : friendIds) {
            userService.getUserById(friendId).ifPresent(friends::add);
        }

        log.debug("Retrieved friends for user {}: {}", userId, friends);
        return friends;
    }
}
