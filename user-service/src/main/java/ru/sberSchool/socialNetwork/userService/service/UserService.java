package ru.sberSchool.socialNetwork.userService.service;

import ru.sberSchool.socialNetwork.userService.exceptions.IncorrectDataException;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for managing users.
 *
 * @author Elland Ilia
 */
public interface UserService {

    /**
     * Creates a new user with the specified username and email.
     *
     * @param username the username of the new user
     * @param email    the email of the new user
     * @return the created {@link User}
     * @throws IncorrectDataException if the provided data is invalid
     */
    User createUser(String username, String email) throws IncorrectDataException;

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId the unique identifier of the user
     * @return an {@link Optional} containing the {@link User} if found, or empty if not found
     */
    Optional<User> getUserById(UUID userId);
}
