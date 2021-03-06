package com.singletonapps.demo.repository;

import com.singletonapps.demo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);

    Game findByYearPublished(Long year);
}
