package com.simgletonapps.repository;

import com.simgletonapps.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {


}
