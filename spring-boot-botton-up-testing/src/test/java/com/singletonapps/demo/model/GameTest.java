package com.singletonapps.demo.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    public void testGameCreationObjectByConstructor() {

        //given
        String name = "Legend of Zelda";
        long yearPublished = 1998L;
        long id = 1L;
        Game game;

        //when
        game = new Game(id, name, yearPublished);

        //then
        assertThat(game.getId()).isEqualTo(id);
        assertThat(game.getName()).isEqualTo(name);
        assertThat(game.getYearPublished()).isEqualTo(yearPublished);
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
            .build();

        //then
        assertThat(game.getId()).isNotNull();
        assertThat(game.getId()).isEqualTo(id);
        assertThat(game.getName()).isEqualTo(name);
        assertThat(game.getYearPublished()).isEqualTo(yearPublished);
    }
}
