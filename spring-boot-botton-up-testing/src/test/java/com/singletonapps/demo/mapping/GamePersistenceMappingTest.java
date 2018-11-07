package com.singletonapps.demo.mapping;

import com.singletonapps.demo.model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class GamePersistenceMappingTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testModelPersistenceAnnotations() {

        //given
        Game game = Game.builder()
            .id(null)
            .name("Mario Bros")
            .yearPublished(1985L)
            .build();

        //when
        Game gameSaved = entityManager.persistFlushFind(game);

        //then
        assertThat(gameSaved.getId()).isEqualTo(1L);
        assertThat(gameSaved.getName()).isEqualTo("Mario Bros");
        assertThat(gameSaved.getYearPublished()).isEqualTo(1985L);

    }
}
