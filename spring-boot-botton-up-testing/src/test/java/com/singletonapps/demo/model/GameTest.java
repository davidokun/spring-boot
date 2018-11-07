package com.singletonapps.demo.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    private LocalDateTime now;

    @Before
    public void setUp() {
        now = LocalDateTime.now();
    }



    @Test
    public void testGameCreationObjectByConstructor() {

        //given
        String name = "Legend of Zelda";
        long yearPublished = 1998L;
        long id = 1L;
        Game game;

        //when
        game = new Game(id, name, yearPublished, now);

        //then
        assertThat(game.getId()).isEqualTo(id);
        assertThat(game.getName()).isEqualTo(name);
        assertThat(game.getYearPublished()).isEqualTo(yearPublished);
        assertThat(game.getCreateOn()).isEqualTo(now);
    }

    @Test
    public void testGameCreationObjectByBuilder() {

        //given
        String name = "Metal Gear";
        long yearPublished = 1997L;
        long id = 2L;
        Game game;

        //when
        game = Game.builder()
            .id(id)
            .name(name)
            .yearPublished(yearPublished)
            .createOn(now)
            .build();

        //then
        assertThat(game.getId()).isNotNull();
        assertThat(game.getId()).isEqualTo(id);
        assertThat(game.getName()).isEqualTo(name);
        assertThat(game.getYearPublished()).isEqualTo(yearPublished);
        assertThat(game.getCreateOn()).isEqualTo(now);
    }
}
