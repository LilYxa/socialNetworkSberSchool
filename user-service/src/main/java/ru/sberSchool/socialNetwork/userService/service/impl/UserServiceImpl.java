package ru.sberSchool.socialNetwork.userService.service.impl;

import ru.sberSchool.socialNetwork.userService.Constants;
import ru.sberSchool.socialNetwork.userService.exceptions.IncorrectDataException;
import ru.sberSchool.socialNetwork.userService.model.User;
import ru.sberSchool.socialNetwork.userService.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class UserServiceImpl implements UserService {

    private final Map<UUID, User> userDatabase = new HashMap<>();

    @Override
    public User createUser(String username, String email) throws IncorrectDataException {
        log.debug("createUser[0]: creating new user: username - {}, email - {}", username, email);

        if (username == null || email == null) {
            throw new IncorrectDataException(Constants.INCORRECT_DATA_MESSAGE);
        }

        UUID userId = UUID.randomUUID();
        User newUser = new User(userId, username, email);
        userDatabase.put(userId, newUser);

        log.debug("createUser[1]: created new user: {}", newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        log.debug("getUserById[0]: fetching user with ID: {}", userId);
        return Optional.ofNullable(userDatabase.get(userId));
    }
}
