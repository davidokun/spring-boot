package com.singletonapps.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singletonapps.demo.dto.GameDTO;
import com.singletonapps.demo.exception.GameNotFoundException;
import com.singletonapps.demo.service.impl.GameServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GameServiceImpl gameService;

    @Test
    public void testCreateGameEndpoint() throws Exception {

        //given
        LocalDateTime now = LocalDateTime.now();
        String name = "Contra";
        long year = 1983L;
        GameDTO gameRequest = GameDTO.builder()
            .id(null)
            .name(name)
            .yearPublished(year)
            .createOn(null)
            .build();

        long id = 1L;
        GameDTO gameResponse = GameDTO.builder()
            .id(id)
            .name(name)
            .yearPublished(year)
            .createOn(now)
            .build();

        given(gameService.createGame(gameRequest))
            .willReturn(gameResponse);

        //when
        ResultActions response = mockMvc.perform(
            post("/games")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(gameRequest)));

        //then
        response
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("@.id").isNotEmpty())
            .andExpect(jsonPath("@.id").value(id))
            .andExpect(jsonPath("@.createOn").isNotEmpty())
            .andExpect(jsonPath("@.name").value(name))
            .andExpect(jsonPath("@.yearPublished").value(year));
    }

    @Test
    public void testGetGameById() throws Exception {

        //given
        Long id = 1L;
        String name = "Metal Gear";
        long year = 2002L;
        LocalDateTime now = LocalDateTime.now();

        GameDTO game = GameDTO.builder()
            .id(id)
            .name(name)
            .yearPublished(year)
            .createOn(now)
            .build();

        given(gameService.findGameById(id)).willReturn(game);

        //when
        ResultActions response = mockMvc.perform(
            get("/games/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath("@.id").value(id))
            .andExpect(jsonPath("@.name").value(name))
            .andExpect(jsonPath("@.yearPublished").value(year))
            .andExpect(jsonPath("@.createOn").value(now.toString()));
    }

    @Test
    public void testGameByIdNotFoundWithStatus404() throws Exception {

        //given
        Long id = 0L;
        String message = "Game with id 0 Not Found";
        given(gameService.findGameById(id))
            .willThrow(new GameNotFoundException(message));

        //when
        ResultActions response = mockMvc.perform(
            get("/games/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        response
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("@.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("@.message").value(message));
    }

    @Test
    public void testGetAllGames() throws Exception {

        //given
        List<GameDTO> gamesRetrieved = new ArrayList<>();

        GameDTO zelda = GameDTO.builder()
            .id(1L)
            .name("Zelda")
            .yearPublished(1998L)
            .createOn(LocalDateTime.now())
            .build();

        GameDTO kirby = GameDTO.builder()
            .id(2L)
            .name("Kirby")
            .yearPublished(1983L)
            .createOn(LocalDateTime.now())
            .build();

        GameDTO mario = GameDTO.builder()
            .id(2L)
            .name("Mario Bros")
            .yearPublished(1985L)
            .createOn(LocalDateTime.now())
            .build();

        gamesRetrieved.add(zelda);
        gamesRetrieved.add(kirby);
        gamesRetrieved.add(mario);

        given(gameService.findAllGames()).willReturn(gamesRetrieved);

        //when
        ResultActions response = mockMvc.perform(
            get("/games")
                .contentType(APPLICATION_JSON_UTF8));

        //then
        response
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("@.[0].id").isNotEmpty())
            .andExpect(jsonPath("@.[0].name").value("Zelda"))
            .andExpect(jsonPath("@.[0].yearPublished").value(1998))
            .andExpect(jsonPath("@.[0].createOn").isNotEmpty())

            .andExpect(jsonPath("@.[1].id").isNotEmpty())
            .andExpect(jsonPath("@.[1].name").value("Kirby"))
            .andExpect(jsonPath("@.[1].yearPublished").value(1983))
            .andExpect(jsonPath("@.[1].createOn").isNotEmpty())

            .andExpect(jsonPath("@.[2].id").isNotEmpty())
            .andExpect(jsonPath("@.[2].name").value("Mario Bros"))
            .andExpect(jsonPath("@.[2].yearPublished").value(1985))
            .andExpect(jsonPath("@.[2].createOn").isNotEmpty());
    }

    @Test
    public void testGivenAExistentGameShoulReturnUpdatedGame() throws Exception {

        //given
        final Long id = 1L;
        final String newName = "Candy Crush";
        final Long newYear = 2015L;

        final GameDTO gameToUpdate = GameDTO.builder()
            .name(newName)
            .yearPublished(newYear)
            .build();

        final GameDTO updateGame = GameDTO.builder()
            .id(id)
            .name(newName)
            .yearPublished(newYear)
            .createOn(LocalDateTime.now())
            .build();

        given(gameService.updateGame(id, gameToUpdate)).willReturn(updateGame);

        //when
        ResultActions response = mockMvc.perform(
            put("/games/" + id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(gameToUpdate))
        );

        //then
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath("@.id").value(id))
            .andExpect(jsonPath("@.name").value(newName))
            .andExpect(jsonPath("@.yearPublished").value(newYear))
            .andExpect(jsonPath("@.createOn").isNotEmpty());
    }

    @Test
    public void testThatUpdatingAGameWithNonExistentIdShouldReturnGameNotFound404() throws Exception {

        //given
        Long id = 502L;
        String message = "Game with id 502 Not Found";
        GameDTO gameToUpdate = GameDTO.builder()
            .name("Some name")
            .yearPublished(1900L)
            .build();

        given(gameService.updateGame(id, gameToUpdate))
            .willThrow(new GameNotFoundException(message));

        //when
        ResultActions response = mockMvc.perform(
            put("/games/" + id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(gameToUpdate))
        );

        //then
        response
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("@.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("@.message").value(message));

    }

    @Test
    public void testDeleteGameById() throws Exception {

        //given
        Long id = 3L;

        //when
        ResultActions response = mockMvc.perform(
            delete("/games/" + id)
        );

        //then
        response
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));

    }

}
