package com.DevConnect.controller;

import com.DevConnect.dto.PostRequest;
import com.DevConnect.entity.Post;
import com.DevConnect.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@Valid @RequestBody PostRequest postRequest) {
        Post post = postService.addPost(postRequest);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Post> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }
}
