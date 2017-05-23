package com.simgletonapps.service.impl;


import com.simgletonapps.dao.IArticleDao;
import com.simgletonapps.model.Article;
import com.simgletonapps.repository.ArticleRepository;
import com.simgletonapps.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService implements IArticleService {

    @Autowired
    private IArticleDao articleDAO;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article getArticleById(int articleId) {
        Article obj = articleDAO.getArticleById(articleId);
        return obj;
    }

    @Override
    public List<Article> getAllArticles(){
        //return articleDAO.getAllArticles();
        return (List<Article>) articleRepository.findAll();
    }

    @Override
    public synchronized boolean addArticle(Article article){
        if (articleDAO.articleExists(article.getTitle(), article.getCategory())) {
            return false;
        } else {
            articleDAO.addArticle(article);
            return true;
        }
    }

    @Override
    public void updateArticle(Article article) {
        articleDAO.updateArticle(article);
    }

    @Override
    public void deleteArticle(int articleId) {
        articleDAO.deleteArticle(articleId);
    }
}
