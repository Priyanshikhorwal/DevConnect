package com.DevConnect.service;

import com.DevConnect.dto.PostRequest;
import com.DevConnect.entity.Post;
import com.DevConnect.entity.User;
import com.DevConnect.exception.ResourceNotFoundException;
import com.DevConnect.repository.PostRepository;
import com.DevConnect.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    public final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post addPost(PostRequest postRequest) {

        // 1. Extract the email (username) of the currently authenticated user from SecurityContext
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Fetch the User entity from the database
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 3. Create and save the Post
        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
