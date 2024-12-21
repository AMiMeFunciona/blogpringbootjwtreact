package com.principal.pruebaspringbootjwt.controller;


import com.principal.pruebaspringbootjwt.dto.PostDTO;
import com.principal.pruebaspringbootjwt.scopes.RequestTimer;
import com.principal.pruebaspringbootjwt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
public class PostController {

    private final PostService postService;
    private final RequestTimer requestTimer;

    public PostController(PostService postService, RequestTimer requestTimer) {
        this.postService = postService;
        this.requestTimer = requestTimer;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PostDTO>> getAllPost() {
        requestTimer.setControllerStartTime();
        List<PostDTO> posts = postService.getAllPostSortedByDate();
        System.out.println("Tiempo del controlador: " + requestTimer.getControllerDuration() + " ms");
        System.out.println("Tiempo total de la solicitud: " + requestTimer.getRequestDuration() + " ms");
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/detail/{id}")
    public ResponseEntity<PostDTO> viewPost(@PathVariable UUID id) {
        PostDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/searchposts")
    public ResponseEntity<List<PostDTO>>listPosts(@RequestParam(value = "title", required = false) String title) {
        List<PostDTO> posts;
        if (title != null && !title.isEmpty()) {
            posts = postService.getPostsByTitle(title);
        } else {
            posts = postService.getAllPostSortedByDate();
        }
        return ResponseEntity.ok(posts);
    }


    @PostMapping("/new")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post) {
        postService.createPost(post);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable UUID id, @RequestBody PostDTO postDTO) {
        boolean postUpdated = postService.updatePost(id, postDTO.getTitle(), postDTO.getBody());
        return (postUpdated) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID id) {
        boolean postDeleted = postService.deletePost(id);
        return (postDeleted) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
