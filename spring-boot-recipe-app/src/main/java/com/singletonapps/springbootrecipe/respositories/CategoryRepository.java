package com.singletonapps.springbootrecipe.respositories;

import com.singletonapps.springbootrecipe.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
