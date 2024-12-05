package ru.sberSchool.socialNetwork.postService.service;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.sberSchool.socialNetwork.postService.model.Post;
//import ru.sberSchool.socialNetwork.postService.service.impl.PostServiceImpl;
//import ru.sberSchool.socialNetwork.userService.model.User;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//import java.util.UUID;
//
//public class PostServiceTest {
//
//    private PostService postService;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        postService = new PostServiceImpl();
//        user = new User(UUID.randomUUID(), "testUser", "test@example.com");
//    }
//
//    @Test
//    void testCreatePost() {
//        String content = "This is a test post";
//
//        Post post = postService.createPost(user, content);
//
//        assertNotNull(post);
//        assertEquals(user, post.getUser());
//        assertEquals(content, post.getContent());
//        assertNotNull(post.getId());
//        assertTrue(post.getTimestamp() > 0);
//    }
//
//    @Test
//    void testGetPostsByUser() {
//        String content1 = "First post";
//        String content2 = "Second post";
//
//        postService.createPost(user, content1);
//        postService.createPost(user, content2);
//
//        List<Post> posts = postService.getPostsByUser(user);
//
//        assertEquals(2, posts.size());
//        assertTrue(posts.stream().allMatch(post -> post.getUser().equals(user)));
//    }
//
//    @Test
//    void testGetPostById() {
//        String content = "This is a test post";
//
//        Post post = postService.createPost(user, content);
//
//        Post retrievedPost = postService.getPostById(post.getId());
//
//        assertNotNull(retrievedPost);
//        assertEquals(post, retrievedPost);
//    }
//
//    @Test
//    void testGetPostById_NotFound() {
//        UUID randomId = UUID.randomUUID();
//
//        Post retrievedPost = postService.getPostById(randomId);
//
//        assertNull(retrievedPost);
//    }
//}
//

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sberSchool.socialNetwork.postService.model.Post;
import ru.sberSchool.socialNetwork.postService.service.impl.PostServiceImpl;
import ru.sberSchool.socialNetwork.userService.model.User;

import java.util.*;

@Slf4j
class PostServiceTest {

    private PostService postService;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostServiceImpl();
    }

    @Test
    void testCreatePost_positive() {
        String content = "This is a test post.";
        when(user.getId()).thenReturn(UUID.randomUUID());
        log.debug("testCreatePost_positive[0]: Testing createPost with user: {}, content: {}", user, content);
        Post createdPost = postService.createPost(user, content);

        assertNotNull(createdPost);
        assertEquals(user, createdPost.getUser());
        assertEquals(content, createdPost.getContent());
        assertNotNull(createdPost.getId());

        log.debug("testCreatePost_positive[1]: Post created successfully: {}", createdPost);
    }

    @Test
    void testGetPostsByUser_positive() {
        String content1 = "First post";
        String content2 = "Second post";

        when(user.getId()).thenReturn(UUID.randomUUID());

        log.debug("testGetPostsByUser_positive[0]: Creating posts for user: {}", user);

        postService.createPost(user, content1);
        postService.createPost(user, content2);

        log.debug("testGetPostsByUser_positive[1]: Fetching posts for user: {}", user);

        List<Post> userPosts = postService.getPostsByUser(user);

        assertEquals(2, userPosts.size());
        assertTrue(userPosts.stream().anyMatch(post -> post.getContent().equals(content1)));
        assertTrue(userPosts.stream().anyMatch(post -> post.getContent().equals(content2)));

        log.debug("testGetPostsByUser_positive[2]: Posts fetched successfully for user: {}", userPosts);
    }

    @Test
    void testGetPostsByUser_negative() {
        User unknownUser = mock(User.class);
        when(unknownUser.getId()).thenReturn(UUID.randomUUID());

        log.debug("testGetPostsByUser_negative[0]: Fetching posts for unknown user: {}", unknownUser);
        List<Post> userPosts = postService.getPostsByUser(unknownUser);
        assertTrue(userPosts.isEmpty());

        log.debug("testGetPostsByUser_negative[1]: No posts found for unknown user: {}", unknownUser);
    }

    @Test
    void testGetPostById_positive() {
        String content = "This is a test post.";
        when(user.getId()).thenReturn(UUID.randomUUID());
        log.debug("testGetPostById_positive[0]: Creating a post for user: {}", user);

        Post createdPost = postService.createPost(user, content);
        log.debug("testGetPostById_positive[1]: Fetching post by ID: {}", createdPost.getId());
        Post fetchedPost = postService.getPostById(createdPost.getId());

        assertNotNull(fetchedPost);
        assertEquals(createdPost, fetchedPost);

        log.debug("testGetPostById_positive[2]: Post fetched successfully: {}", fetchedPost);
    }

    @Test
    void testGetPostById_negative() {
        UUID nonExistentId = UUID.randomUUID();
        log.debug("testGetPostById_negative[0]: Fetching post with non-existent ID: {}", nonExistentId);
        Post fetchedPost = postService.getPostById(nonExistentId);
        assertNull(fetchedPost);
        log.debug("testGetPostById_negative[1]: No post found with ID: {}", nonExistentId);
    }
}
