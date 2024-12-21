package com.principal.pruebaspringbootjwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.principal.pruebaspringbootjwt.dto.PostDTO;
import com.principal.pruebaspringbootjwt.model.Post;
import com.principal.pruebaspringbootjwt.repository.PostRepository;
import com.principal.pruebaspringbootjwt.service.PostService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    private static List<Post> getMockPosts() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<Post> mockPosts;
        try {
            mockPosts = objectMapper.readValue(
                    Paths.get("src/test/resources/posts.json").toFile(),
                    new TypeReference<List<Post>>() {
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mockPosts;
    }

    @Test
    void testGetPostsByTitle() {

        final List<Post> mockPosts = getMockPosts();
        List<PostDTO> lstPostDTORepository = mockPosts.stream()
                .map(post -> new ModelMapper().map(post, PostDTO.class))
                .toList();
        when(postRepository.findByTitleFetching("segundo")).thenReturn(lstPostDTORepository);
        List<PostDTO> lstPostDTO = postService.getPostsByTitle("segundo");
        assertEquals(lstPostDTORepository.getFirst().getTitle(), lstPostDTO.getFirst().getTitle());
        verify(postRepository, times(1)).findByTitleFetching("segundo");
    }

    @Test
    void testGetAllPostsSortedByDate() {

        final List<Post> mockPosts = getMockPosts();
        when(postRepository.findAllPostsOrderedByPublicationDateDesc()).thenReturn(mockPosts);
        List<PostDTO> postDTOS = postService.getAllPostSortedByDate();
        assertEquals(3, postDTOS.size());
        assertEquals("Post 1", postDTOS.getFirst().getTitle());
        verify(postRepository, times(1)).findAllPostsOrderedByPublicationDateDesc();
    }

    @Test
    @WithMockUser(username = "roberto@hotmail.com", authorities = { "ADMIN", "USER" })
    void testCreatePost() {

        final List<Post> mockPosts = getMockPosts();
        Post firstPost = mockPosts.getFirst();
        when(postRepository.save(any(Post.class))).thenReturn(firstPost);
        PostDTO savedPost = postService.createPost(new ModelMapper().map(firstPost, PostDTO.class));
        assertEquals(UUID.fromString("cd48fd33-c06c-487c-a4be-efc3c0f740a1"), savedPost.getId());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testUpdatePost() {

        final List<Post> mockPosts = getMockPosts();
        Post firstPost = mockPosts.getFirst();
        firstPost.setTitle("nuevo titulo de primer post");
        when(postRepository.updatePost(eq(firstPost.getId()), eq(firstPost.getTitle()), eq(firstPost.getBody()))).thenReturn(1);
        boolean result = postService.updatePost(firstPost.getId(), firstPost.getTitle(), firstPost.getBody());
        assertTrue(result);
        verify(postRepository, times(1)).updatePost(eq(firstPost.getId()), eq(firstPost.getTitle()), eq(firstPost.getBody()));
    }

    @Test
    void testUpdatePostNoRowsAffected() {
        UUID postId = UUID.randomUUID();
        String nuevoTitulo = "titulo no afecta";
        String nuevoBody = "body no afecta";
        when(postRepository.updatePost(eq(postId), eq(nuevoTitulo), eq(nuevoBody))).thenReturn(0);
        boolean result = postService.updatePost(postId, nuevoTitulo, nuevoBody);
        assertFalse(result);
        verify(postRepository, times(1)).updatePost(eq(postId), eq(nuevoTitulo), eq(nuevoBody));
    }

    @Test
    void testDeletePost() {
        final List<Post> mockPosts = getMockPosts();
        Post firstPost = mockPosts.getFirst();
        when(postRepository.deletePostById(eq(firstPost.getId()))).thenReturn(1);
        boolean result = postService.deletePost(firstPost.getId());
        assertTrue(result);
        verify(postRepository, times(1)).deletePostById(eq(firstPost.getId()));
    }

    @Test
    void testDeletePostNoRowsAffected() {
        UUID postId = UUID.randomUUID();
        when(postRepository.deletePostById(eq(postId))).thenReturn(0);
        boolean result = postService.deletePost(postId);
        assertFalse(result);
        verify(postRepository, times(1)).deletePostById(eq(postId));
    }

}
