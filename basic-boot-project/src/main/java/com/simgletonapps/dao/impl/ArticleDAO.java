package com.simgletonapps.dao.impl;


import com.simgletonapps.dao.IArticleDao;
import com.simgletonapps.model.Article;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ArticleDAO implements IArticleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Article> getAllArticles() {
        String query = "FROM Article as atcl ORDER BY atcl.articleId";
        return (List<Article>) entityManager.createQuery(query).getResultList();
    }

    @Override
    public Article getArticleById(int articleId) {
        return entityManager.find(Article.class, articleId);
    }

    @Override
    public void addArticle(Article article) {
        entityManager.persist(article);
    }

    @Override
    public void updateArticle(Article article) {
        Article art = getArticleById(article.getArticleId());
        art.setTitle(article.getTitle());
        art.setCategory(article.getCategory());
        entityManager.flush();
    }

    @Override
    public void deleteArticle(int articleId) {
        entityManager.remove(getArticleById(articleId));
    }

    @Override
    public boolean articleExists(String title, String location) {
        String hql = "FROM Article as atcl WHERE atcl.title = ? and atcl.category = ?";
        int count =
                entityManager.createQuery(hql)
                        .setParameter(1, title)
                        .setParameter(2, location).getResultList().size();
        return count > 0 ? true : false;
    }
}
