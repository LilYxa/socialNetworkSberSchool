package ru.sberSchool.socialNetwork.chatService.service.impl;

import ru.sberSchool.socialNetwork.chatService.model.Message;
import ru.sberSchool.socialNetwork.chatService.service.ChatService;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChatServiceImpl implements ChatService {

    private final List<Message> messages = new ArrayList<>();

    @Override
    public Message sendMessage(User sender, User receiver, String content) {
        UUID messageId = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        Message message = new Message(messageId, sender, receiver, content, timestamp);
        messages.add(message);
        return message;
    }

    @Override
    public List<Message> getMessagesByUser(User user) {
        return messages.stream()
                .filter(message -> message.getSender().equals(user) || message.getReceiver().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public Message getMessageById(UUID id) {
        return messages.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

