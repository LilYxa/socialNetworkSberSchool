package ru.sberSchool.socialNetwork.chatService.service;

import ru.sberSchool.socialNetwork.chatService.model.Message;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing chat functionality.
 *
 * @author Elland Ilia
 */
public interface ChatService {

    /**
     * Sends a message from one user to another.
     *
     * @param sender   the user sending the message
     * @param receiver the user receiving the message
     * @param content  the content of the message
     * @return the sent {@link Message}
     */
    Message sendMessage(User sender, User receiver, String content);

    /**
     * Retrieves all messages involving the specified user as sender or receiver.
     *
     * @param user the user whose messages to retrieve
     * @return a list of {@link Message} objects involving the specified user
     */
    List<Message> getMessagesByUser(User user);

    /**
     * Retrieves a message by its unique identifier.
     *
     * @param id the unique identifier of the message
     * @return the {@link Message} if found, or null if not found
     */
    Message getMessageById(UUID id);
}
