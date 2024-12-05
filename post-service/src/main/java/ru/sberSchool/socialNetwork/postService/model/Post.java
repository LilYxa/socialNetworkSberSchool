package ru.sberSchool.socialNetwork.postService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private UUID id;
    private User user;
    private String content;
    private long timestamp;
}
