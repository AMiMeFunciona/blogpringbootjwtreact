package com.principal.pruebaspringbootjwt.service;


import com.principal.pruebaspringbootjwt.dto.CommentDTO;
import com.principal.pruebaspringbootjwt.model.Comment;
import com.principal.pruebaspringbootjwt.model.Post;
import com.principal.pruebaspringbootjwt.model.User;
import com.principal.pruebaspringbootjwt.repository.CommentRepository;
import com.principal.pruebaspringbootjwt.repository.PostRepository;
import com.principal.pruebaspringbootjwt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> new ModelMapper().map(comment, CommentDTO.class))
                .collect(Collectors.toList());

    }

    public Page<CommentDTO> getCommentsByPostId(UUID postId, Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findCommentsByPostId(postId, pageable);
        return commentsPage.map(comment -> new ModelMapper().map(comment, CommentDTO.class));
    }

    public CommentDTO getCommentById(UUID id) {
        return commentRepository.findById(id)
                .stream()
                .map(comment -> new ModelMapper().map(comment, CommentDTO.class))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Transactional
    public int updateComment(UUID id, String body) {
        return commentRepository.updateComment(id, body);
    }

    @Transactional
    public void createComment(UUID postId, CommentDTO commentDTO) {

        Comment comment = new Comment();
        comment.setBody(commentDTO.getBody());
        comment.setPublicationDate(LocalDateTime.now());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);

        commentRepository.save(comment);

    }

    @Transactional
    public void deleteComment(UUID commentId) {
        commentRepository.deleteById(commentId);
    }
}
