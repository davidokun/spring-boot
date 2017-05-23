package com.simgletonapps.service;


import com.simgletonapps.model.Article;
import java.util.List;

public interface IArticleService {

    Article getArticleByIdRepoQuery(int articleId);

    List<Article> getAllArticles();
    Article getArticleById(int articleId);
    List<Article> getAllArticlesRepo();
    List<Article> getAllArticlesRepoQuery();
    boolean addArticle(Article article);
    void updateArticle(Article article);
    void deleteArticle(int articleId);
}
