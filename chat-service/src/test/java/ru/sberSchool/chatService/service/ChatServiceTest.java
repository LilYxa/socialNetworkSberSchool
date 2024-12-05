package ru.sberSchool.chatService.service;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.sberSchool.socialNetwork.chatService.model.Message;
//import ru.sberSchool.socialNetwork.chatService.service.ChatService;
//import ru.sberSchool.socialNetwork.chatService.service.impl.ChatServiceImpl;
//import ru.sberSchool.socialNetwork.userService.model.User;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ChatServiceTest {
//
//    private ChatService chatService;
//    private User sender;
//    private User receiver;
//
//    @BeforeEach
//    void setUp() {
//        chatService = new ChatServiceImpl();
//        sender = new User(UUID.randomUUID(), "senderUser", "sender@example.com");
//        receiver = new User(UUID.randomUUID(), "receiverUser", "receiver@example.com");
//    }
//
//    @Test
//    void testSendMessage() {
//        String content = "Hello, this is a test message";
//
//        Message message = chatService.sendMessage(sender, receiver, content);
//
//        assertNotNull(message);
//        assertEquals(sender, message.getSender());
//        assertEquals(receiver, message.getReceiver());
//        assertEquals(content, message.getContent());
//        assertNotNull(message.getId());
//        assertTrue(message.getTimestamp() > 0);
//    }
//
//    @Test
//    void testGetMessagesByUser() {
//        String content1 = "Hello, message 1";
//        String content2 = "Hello, message 2";
//
//        chatService.sendMessage(sender, receiver, content1);
//        chatService.sendMessage(receiver, sender, content2);
//
//        List<Message> messages = chatService.getMessagesByUser(receiver);
//
//        assertEquals(2, messages.size());
//        assertTrue(messages.stream().anyMatch(message -> message.getReceiver().equals(receiver) || message.getSender().equals(receiver)));
//    }
//
//    @Test
//    void testGetMessageById() {
//        String content = "This is a test message";
//
//        Message message = chatService.sendMessage(sender, receiver, content);
//
//        Message retrievedMessage = chatService.getMessageById(message.getId());
//
//        assertNotNull(retrievedMessage);
//        assertEquals(message, retrievedMessage);
//    }
//
//    @Test
//    void testGetMessageById_NotFound() {
//        UUID randomId = UUID.randomUUID();
//
//        Message retrievedMessage = chatService.getMessageById(randomId);
//
//        assertNull(retrievedMessage);
//    }
//}

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.sberSchool.socialNetwork.chatService.model.Message;
import ru.sberSchool.socialNetwork.chatService.service.ChatService;
import ru.sberSchool.socialNetwork.chatService.service.impl.ChatServiceImpl;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.*;

@Slf4j
class ChatServiceTest {

    private ChatService chatService;

    @Mock
    private User sender;

    @Mock
    private User receiver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chatService = new ChatServiceImpl();
    }

    @Test
    void testSendMessage_positive() {
        String content = "Hello, how are you?";

        when(sender.getId()).thenReturn(UUID.randomUUID());
        when(receiver.getId()).thenReturn(UUID.randomUUID());

        log.debug("testSendMessage_positive[0]: Testing sendMessage with sender: {}, receiver: {}, content: {}", sender, receiver, content);
        Message sentMessage = chatService.sendMessage(sender, receiver, content);

        assertNotNull(sentMessage);
        assertEquals(sender, sentMessage.getSender());
        assertEquals(receiver, sentMessage.getReceiver());
        assertEquals(content, sentMessage.getContent());
        assertNotNull(sentMessage.getId());

        log.debug("testSendMessage_positive[1]: Message sent successfully: {}", sentMessage);
    }

    @Test
    void testGetMessagesByUser_positive() {
        String content1 = "Hello!";
        String content2 = "How are you?";
        when(sender.getId()).thenReturn(UUID.randomUUID());
        when(receiver.getId()).thenReturn(UUID.randomUUID());

        log.debug("testGetMessagesByUser_positive[0]: Sending test messages.");
        chatService.sendMessage(sender, receiver, content1);
        chatService.sendMessage(receiver, sender, content2);

        log.debug("testGetMessagesByUser_positive[1]: Fetching messages for sender: {}", sender);
        List<Message> messages = chatService.getMessagesByUser(sender);

        assertEquals(2, messages.size());
        assertTrue(messages.stream().anyMatch(msg -> msg.getContent().equals(content1)));
        assertTrue(messages.stream().anyMatch(msg -> msg.getContent().equals(content2)));

        log.debug("testGetMessagesByUser_positive[2]: Messages fetched successfully for sender: {}", messages);
    }

    @Test
    void testGetMessagesByUser_negative() {
        User unknownUser = mock(User.class);
        when(unknownUser.getId()).thenReturn(UUID.randomUUID());

        log.debug("testGetMessagesByUser_negative[0]: Fetching messages for unknown user: {}", unknownUser);
        List<Message> messages = chatService.getMessagesByUser(unknownUser);
        assertTrue(messages.isEmpty());

        log.debug("testGetMessagesByUser_negative[1]: No messages found for unknown user: {}", unknownUser);
    }

    @Test
    void testGetMessageById_positive() {
        String content = "Test message";

        when(sender.getId()).thenReturn(UUID.randomUUID());
        when(receiver.getId()).thenReturn(UUID.randomUUID());

        log.debug("testGetMessageById_positive[0]: Sending test message.");
        Message sentMessage = chatService.sendMessage(sender, receiver, content);

        log.debug("testGetMessageById_positive[1]: Fetching message by ID: {}", sentMessage.getId());
        Message fetchedMessage = chatService.getMessageById(sentMessage.getId());

        assertNotNull(fetchedMessage);
        assertEquals(sentMessage, fetchedMessage);

        log.debug("testGetMessageById_positive[2]: Message fetched successfully: {}", fetchedMessage);
    }

    @Test
    void testGetMessageById_negative() {
        UUID nonExistentId = UUID.randomUUID();
        log.debug("testGetMessageById_negative[0]: Fetching message with non-existent ID: {}", nonExistentId);
        Message fetchedMessage = chatService.getMessageById(nonExistentId);
        assertNull(fetchedMessage);
        log.debug("testGetMessageById_negative[1]: No message found with ID: {}", nonExistentId);
    }
}


