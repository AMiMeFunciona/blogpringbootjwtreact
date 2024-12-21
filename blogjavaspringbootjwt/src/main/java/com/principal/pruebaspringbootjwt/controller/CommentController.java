package com.principal.pruebaspringbootjwt.controller;


import com.principal.pruebaspringbootjwt.dto.CommentDTO;
import com.principal.pruebaspringbootjwt.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<Page<CommentDTO>> getComments(@PathVariable UUID postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)  {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentDTO> comments = commentService.getCommentsByPostId(postId, pageable);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/detail/{id}")
    public ResponseEntity<CommentDTO> viewComment(@PathVariable UUID id) {
        CommentDTO commentDTO = commentService.getCommentById(id);
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<CommentDTO> createComment(@RequestParam UUID postId, @RequestParam String body) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBody(body);
        commentService.createComment(postId, commentDTO);
        return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable UUID id,@RequestBody CommentDTO commentDTO) {
        int rowsAffected = commentService.updateComment(id, commentDTO.getBody());
        return rowsAffected > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

}
