package com.singletonapps.demospringcache.controller;

import com.singletonapps.demospringcache.model.Post;
import com.singletonapps.demospringcache.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    public Post getPosts(@PathVariable Long id) {
        return postService.getPostById(id).orElse(new Post());
    }

    @DeleteMapping("/cache")
    public void clearCache() {
        postService.clearCache();
    }
}
