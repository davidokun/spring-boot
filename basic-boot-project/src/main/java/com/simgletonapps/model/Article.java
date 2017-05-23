package com.simgletonapps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "articles")
@XmlRootElement(name = "article")
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
