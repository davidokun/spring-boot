package com.simgletonapps.controller;

import com.simgletonapps.model.Article;
import com.simgletonapps.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("user")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping("articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") Integer id) {
        Article article = articleService.getArticleById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> list = articleService.getAllArticles();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("articles")
    public ResponseEntity<Void> addArticle(@RequestBody Article article, UriComponentsBuilder builder) {
        boolean flag = articleService.addArticle(article);
        if (flag == false) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/article/{id}").buildAndExpand(article.getArticleId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("articles")
    public ResponseEntity<Article> updateArticle(@RequestBody Article article) {
        articleService.updateArticle(article);
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    @DeleteMapping("articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
