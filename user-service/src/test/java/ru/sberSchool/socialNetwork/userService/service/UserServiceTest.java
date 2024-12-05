package ru.sberSchool.socialNetwork.userService.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberSchool.socialNetwork.userService.exceptions.IncorrectDataException;
import ru.sberSchool.socialNetwork.userService.model.User;
import ru.sberSchool.socialNetwork.userService.service.impl.UserServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    void testCreateUser() throws IncorrectDataException {
        log.debug("testCreateUser[0]: start test");
        String username = "testUser";
        String email = "test@example.com";

        User user = userService.createUser(username, email);
        log.debug("testCreateUser[1]: user: {}", user);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertNotNull(user.getId());
    }

    @Test
    void testCreateUser_negative() {
        log.debug("testCreateUser_negative[0]: start test");
        String username = null;
        String email = null;

        log.debug("testCreateUser_negative[1]: Testing createUser with null inputs.");

        Exception exception = assertThrows(IncorrectDataException.class, () -> userService.createUser(username, email));

        log.debug("testCreateUser_negative[2]: Exception thrown as expected: {}", exception.getMessage());
    }

    @Test
    void testGetUserById() throws IncorrectDataException {
        log.debug("testGetUserById[0]: start test");
        String username = "testUser";
        String email = "test@example.com";
        User user = userService.createUser(username, email);

        Optional<User> retrievedUser = userService.getUserById(user.getId());
        log.debug("testGetUserById[1]: retrieved user: {}", retrievedUser);

        assertTrue(retrievedUser.isPresent());
        assertEquals(user, retrievedUser.get());
    }

    @Test
    void testGetUserById_NotFound() {
        log.debug("testGetUserById_NotFound[0]: start test");
        UUID randomId = UUID.randomUUID();
        log.debug("testGetUserById_NotFound[1]: getting user with non existent id: {}", randomId);

        Optional<User> retrievedUser = userService.getUserById(randomId);

        assertFalse(retrievedUser.isPresent());
    }


}
