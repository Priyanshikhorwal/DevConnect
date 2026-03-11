package com.DevConnect.controller;

import com.DevConnect.dto.PostRequest;
import com.DevConnect.entity.Post;
import com.DevConnect.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }
    @PostMapping
    public Post addPost(@RequestBody PostRequest postRequest){
        return postService.addPost(postRequest);
    }
    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }
}
