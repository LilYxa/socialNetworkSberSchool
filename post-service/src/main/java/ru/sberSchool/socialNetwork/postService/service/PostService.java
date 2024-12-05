package ru.sberSchool.socialNetwork.postService.service;

import ru.sberSchool.socialNetwork.postService.model.Post;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing posts.
 */
public interface PostService {

    /**
     * Creates a new post for a specified user.
     *
     * @param user    the user creating the post
     * @param content the content of the post
     * @return the created {@link Post}
     */
    Post createPost(User user, String content);

    /**
     * Retrieves all posts created by a specified user.
     *
     * @param user the user whose posts to retrieve
     * @return a list of {@link Post} objects created by the specified user
     */
    List<Post> getPostsByUser(User user);

    /**
     * Retrieves a post by its unique identifier.
     *
     * @param id the unique identifier of the post
     * @return the {@link Post} if found, or null if not found
     */
    Post getPostById(UUID id);
}
