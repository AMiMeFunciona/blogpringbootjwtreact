package com.principal.pruebaspringbootjwt.service;

import com.principal.pruebaspringbootjwt.dto.PostDTO;
import com.principal.pruebaspringbootjwt.exceptions.EntityNotFoundException;
import com.principal.pruebaspringbootjwt.model.Post;
import com.principal.pruebaspringbootjwt.model.User;
import com.principal.pruebaspringbootjwt.repository.PostRepository;
import com.principal.pruebaspringbootjwt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public List<PostDTO> getAllPostSortedByDate() {

//      return postRepository.findAll(Sort.by("publicationDate").descending())
//               .stream()
//               .map(post -> new ModelMapper().map(post, PostDTO.class))
//               .toList();

        return postRepository
                .findAllPostsOrderedByPublicationDateDesc()
                .stream()
                .map(post -> {
                    PostDTO postDTO = new PostDTO();
                    postDTO.setId(post.getId());
                    postDTO.setTitle(post.getTitle());
                    postDTO.setBody(post.getBody());
                    postDTO.setPublicationDate(post.getPublicationDate());
                    postDTO.setUserEmail(post.getUser() != null ? post.getUser().getEmail() : null);
                    postDTO.setCommentCount(post.getComments() != null ? post.getComments().size() : 0);
                    return postDTO;
                }).collect(Collectors.toList());

    }

    public PostDTO getPostById(UUID id) {
         return postRepository.findPostById(id)
                 .stream()
                 .map(post -> new ModelMapper().map(post, PostDTO.class))
                 .findAny()
                 .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public List<PostDTO> getPostsByTitle(String title) {
//        return postRepository.findByTitleContainingIgnoreCase(title)
//                .stream()
//                .map(post -> new ModelMapper().map(post, PostDTO.class))
//                .toList();

//        return postRepository.findByTitleNativeQuery(title)
//                .stream()
//                .map(post -> new ModelMapper().map(post, PostDTO.class))
//                .toList();
          return postRepository.findByTitleFetching(title)
                  .stream()
                  .map(post -> new ModelMapper().map(post, PostDTO.class))
                  .toList();
    }

    public static String getAuthenticatedEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        } else {
            throw new IllegalStateException("El principal no es de un tipo esperado: " + principal.getClass());
        }
    }

    public PostDTO createPost(PostDTO postDTO) {

        Post post = new Post();
        //UUID postId = UUID.randomUUID();
        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());
        post.setPublicationDate(LocalDateTime.now());
        String email = getAuthenticatedEmail();

//      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//      String email = ((UserDetails) principal).getUsername();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return new ModelMapper().map(savedPost, PostDTO.class);
   //     postRepository.insertPost(postId, postDTO.getTitle(),  postDTO.getBody(), LocalDateTime.now(), user.getId());
    }

    @Transactional
    public boolean updatePost(UUID id, String title, String body) {
        int rowsUpdated = postRepository.updatePost(id, title, body);
        return rowsUpdated > 0;
    }

    @Transactional
    public boolean deletePost(UUID id) {
        int rowsDeleted = postRepository.deletePostById(id);
        return rowsDeleted > 0;
    }
}
