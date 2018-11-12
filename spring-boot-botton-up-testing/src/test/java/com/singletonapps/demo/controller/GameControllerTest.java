package com.singletonapps.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singletonapps.demo.dto.GameDTO;
import com.singletonapps.demo.exception.GameNotFoundException;
import com.singletonapps.demo.service.impl.GameServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
            .andExpect(jsonPath("@.createOn").value(now.toString()))
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
}
