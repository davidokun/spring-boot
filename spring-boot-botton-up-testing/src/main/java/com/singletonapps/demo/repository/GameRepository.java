package com.singletonapps.demo.repository;

import com.singletonapps.demo.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    Game findByName(String name);

    Game findByYearPublished(Long year);
}
