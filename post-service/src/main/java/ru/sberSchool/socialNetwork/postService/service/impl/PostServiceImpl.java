package ru.sberSchool.socialNetwork.postService.service.impl;

import ru.sberSchool.socialNetwork.postService.model.Post;
import ru.sberSchool.socialNetwork.postService.service.PostService;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {

    private final List<Post> posts = new ArrayList<>();

    @Override
    public Post createPost(User user, String content) {
        UUID postId = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        Post post = new Post(postId, user, content, timestamp);
        posts.add(post);
        return post;
    }

    @Override
    public List<Post> getPostsByUser(User user) {
        return posts.stream()
                .filter(post -> post.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public Post getPostById(UUID id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
