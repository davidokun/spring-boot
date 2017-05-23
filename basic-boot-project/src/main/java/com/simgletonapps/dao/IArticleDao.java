package com.simgletonapps.dao;


import com.simgletonapps.model.Article;

import java.util.List;

public interface IArticleDao {

    List<Article> getAllArticles();

    Article getArticleById(int articleId);

    void addArticle(Article article);

    void updateArticle(Article article);

    void deleteArticle(int articleId);

    boolean articleExists(String title, String location);
}

