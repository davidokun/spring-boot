package com.singletonapps.demo.repository;

import com.singletonapps.demo.model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testFindByName() {

        //given
        String name = "Bioshock";
        long yearPublished = 2005L;
        Game game = Game.builder()
            .id(null)
            .name(name)
            .yearPublished(yearPublished)
            .build();
        gameRepository.save(game);

        //when
        Game byName = gameRepository.findByName(name);

        //then
        assertThat(byName.getName()).isEqualTo(name);
        assertThat(byName.getId()).isGreaterThan(0);
        assertThat(byName.getYearPublished()).isEqualTo(yearPublished);
    }

    @Test
    public void testFindByYearPublished() {

        //given
        String name = "Devil May Cry";
        long yearPublished = 2000L;
        Game game = Game.builder()
            .id(null)
            .name(name)
            .yearPublished(yearPublished)
            .build();
        gameRepository.save(game);

        //when
        Game byYearPublished = gameRepository.findByYearPublished(yearPublished);

        //then
        assertThat(byYearPublished.getId()).isNotNull();
        assertThat(byYearPublished.getId()).isGreaterThan(0);
        assertThat(byYearPublished.getName()).isEqualTo(name);
        assertThat(byYearPublished.getYearPublished()).isEqualTo(yearPublished);


    }
}
