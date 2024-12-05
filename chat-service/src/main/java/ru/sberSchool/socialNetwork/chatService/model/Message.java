package ru.sberSchool.socialNetwork.chatService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private UUID id;
    private User sender;
    private User receiver;
    private String content;
    private long timestamp;
}
