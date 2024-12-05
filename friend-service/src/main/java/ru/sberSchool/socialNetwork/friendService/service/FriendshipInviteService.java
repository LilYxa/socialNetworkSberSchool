package ru.sberSchool.socialNetwork.friendService.service;

import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing friendship invitations and relationships.
 */
public interface FriendshipInviteService {

    /**
     * Adds a friendship between two users.
     *
     * @param userId   the unique identifier of the first user
     * @param friendId the unique identifier of the second user
     */
    void addFriend(UUID userId, UUID friendId);

    /**
     * Removes an existing friendship between two users.
     *
     * @param userId   the unique identifier of the first user
     * @param friendId the unique identifier of the second user
     */
    void removeFriend(UUID userId, UUID friendId);

    /**
     * Retrieves a list of friends for a given user.
     *
     * @param userId the unique identifier of the user
     * @return a list of {@link User} objects representing the friends of the user
     */
    List<User> getFriends(UUID userId);

}
