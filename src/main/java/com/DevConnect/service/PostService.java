package com.DevConnect.service;

import com.DevConnect.dto.PostRequest;
import com.DevConnect.entity.Post;
import com.DevConnect.entity.User;
import com.DevConnect.repository.PostRepository;
import com.DevConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Post addPost(PostRequest postRequest){

        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

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
