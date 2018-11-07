package com.singletonapps.demo.mapper;

import com.singletonapps.demo.dto.GameDTO;
import com.singletonapps.demo.dto.GameMapper;
import com.singletonapps.demo.model.Game;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class GameMapperTest {

    @Test
    public void gameToGameDto() {

        //given
        LocalDateTime now = LocalDateTime.now();
        Game game = Game.builder()
            .id(1L)
            .name("ToDto")
            .yearPublished(2015L)
            .createOn(now)
            .build();

        //when
        GameDTO gameDTO = GameMapper.INSTANCE.gameToGameDto(game);

        //then
        assertThat(gameDTO.getId()).isEqualTo(1);
        assertThat(gameDTO.getName()).isEqualTo("ToDto");
        assertThat(gameDTO.getYearPublished()).isEqualTo(2015L);
        assertThat(gameDTO.getCreateOn()).isEqualTo(now);

    }


    @Test
    public void gameDtoToGame() {

        //given
        LocalDateTime now = LocalDateTime.now();
        GameDTO gameDTO = GameDTO.builder()
            .id(2L)
            .name("ToEntity")
            .yearPublished(2016L)
            .createOn(now)
            .build();

        //when
        Game game = GameMapper.INSTANCE.gameDtoToGame(gameDTO);

        //then
        assertThat(game.getId()).isEqualTo(2L);
        assertThat(game.getName()).isEqualTo("ToEntity");
        assertThat(game.getYearPublished()).isEqualTo(2016L);
        assertThat(game.getCreateOn()).isEqualTo(now);
    }

    @Test
    public void testMapGameDtoNullFromGameNul() {

        //given
        Game game = null;

        //when
        GameDTO gameDTO = GameMapper.INSTANCE.gameToGameDto(game);

        //then
        assertThat(gameDTO).isNull();

    }

    @Test
    public void testMapGameNullFromGameDtoNul() {

        //given
        GameDTO gameDTO = null;

        //when
        Game game = GameMapper.INSTANCE.gameDtoToGame(gameDTO);

        //then
        assertThat(game).isNull();

    }
}