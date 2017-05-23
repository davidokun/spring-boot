package com.simgletonapps.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "articles")
@XmlRootElement(name = "article")
@NamedQueries({
        @NamedQuery(name = "Article.findAllArticles",
                    query = "FROM Article as a ORDER BY a.articleId"),
        @NamedQuery(name = "Article.findArticleById",
                    query = "FROM Article as a WHERE a.articleId = ?1")
})
public class Article {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlAttribute
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="article_id")
    private int articleId;

    @XmlAttribute
    @Column(name="title")
    private String title;

    @XmlAttribute
    @Column(name="category")
    private String category;

    @Version
    private int version;

    public Article() {
    }

    public Article(String title, String category) {
        this.title = title;
        this.category = category;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
