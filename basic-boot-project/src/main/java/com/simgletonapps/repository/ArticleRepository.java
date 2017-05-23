package com.simgletonapps.repository;

import com.simgletonapps.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {

    List<Article> findAllArticles();
    Article findArticleById(int articleId);
}
