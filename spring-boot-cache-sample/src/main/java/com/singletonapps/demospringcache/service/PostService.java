package com.singletonapps.demospringcache.service;

import com.singletonapps.demospringcache.model.Author;
import com.singletonapps.demospringcache.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "posts")
public class PostService {

    private static Logger LOG = LoggerFactory.getLogger(PostService.class);

    private List<Post> mockPosts = new ArrayList<>();

    @PostConstruct
    public void initMockData() {

        Author author = new Author();
        author.setId(1L);
        author.setFirstName("Super John");
        author.setLastName("Doe");
        author.setAge(32);

        Author author2 = new Author();
        author2.setId(2L);
        author2.setFirstName("Jane");
        author2.setLastName("Doe");
        author2.setAge(25);

        Post post = new Post();
        post.setId(1L);
        post.setAuthor(author);
        post.setBody("This is a post 1!");
        post.setPostedOn(new Date());

        Post post2 = new Post();
        post2.setId(2L);
        post2.setAuthor(author2);
        post2.setBody("This is a post 2");
        post2.setPostedOn(new Date());

        mockPosts.add(post);
        mockPosts.add(post2);

    }

    @Cacheable
    public List<Post> getPosts() {
        LOG.info("Method getPosts() executed!!");
        return mockPosts;
    }

    @Cacheable
    public Optional<Post> getPostById(Long id) {
        LOG.info("Method getPostsById() executed with Id {}", id);
        return mockPosts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @CacheEvict(cacheNames = "posts", allEntries = true)
    public void clearCache(){}
}
