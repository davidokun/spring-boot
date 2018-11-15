package com.singletonapps.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singletonapps.demo.dto.GameDTO;
import com.singletonapps.demo.model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = BottomUpTestingApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:testData.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:delete.sql", executionPhase = AFTER_TEST_METHOD)
public class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenGameWhenPostGameThenCreateGameWithStatus201() throws Exception {

        //given
        String name = "Axelay";
        long year = 1992L;
        Game game = Game.builder()
            .id(null)
            .name(name)
            .yearPublished(year)
            .build();

        //when
        ResultActions response = mockMvc.perform(
            post("/games")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(game)));

        //then
        response
            .andExpect(status().isCreated())
            .andExpect(jsonPath("@.id").isNotEmpty())
            .andExpect(jsonPath("@.createOn").isNotEmpty())
            .andExpect(jsonPath("@.name").value(name))
            .andExpect(jsonPath("@.yearPublished").value(year));
    }

    @Test
    public void givenMalformedGameWhenPostGameThenReturnStatus400() throws Exception {

        //given
        String malformedGame = "{name: \"Dig Dug\"}";

        //when
        ResultActions response = mockMvc.perform(
            post("/games")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(malformedGame));

        //then
        response
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAGameIdShouldReturnThatGame() throws Exception {

        //given
        Long id = 3L;
        String name = "Mario";
        long year = 1995L;

        //when
        ResultActions response = mockMvc.perform(get("/games/" + id));

        //then
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath("@.id").value(id))
            .andExpect(jsonPath("@.name").value(name))
            .andExpect(jsonPath("@.yearPublished").value(year))
            .andExpect(jsonPath("@.createOn").isNotEmpty());

    }

    @Test
    public void givenAGameNonExistentIdShouldReturnNotFound() throws Exception {

        //given
        Long id = 12L;

        //when
        ResultActions response = mockMvc.perform(get("/games/" + id));

        //then
        response
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("@.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("@.message").value("Game with id [12] not found"));

    }

    @Test
    public void testShouldReturnAllGames() throws Exception {

        //given
        String endpoint = "/games";

        //when
        ResultActions response = mockMvc.perform(
            get(endpoint)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        response
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("@.[0].id").isNotEmpty())
            .andExpect(jsonPath("@.[0].name").value("Zelda"))
            .andExpect(jsonPath("@.[0].yearPublished").value(1998))
            .andExpect(jsonPath("@.[0].createOn").isNotEmpty())

            .andExpect(jsonPath("@.[1].id").isNotEmpty())
            .andExpect(jsonPath("@.[1].name").value("Asterix"))
            .andExpect(jsonPath("@.[1].yearPublished").value(2001))
            .andExpect(jsonPath("@.[1].createOn").isNotEmpty())

            .andExpect(jsonPath("@.[2].id").isNotEmpty())
            .andExpect(jsonPath("@.[2].name").value("Mario"))
            .andExpect(jsonPath("@.[2].yearPublished").value(1995))
            .andExpect(jsonPath("@.[2].createOn").isNotEmpty());

    }

    @Test
    public void testThatAnExistingGameCanBeUpdated() throws Exception {

        //given
        Long id = 1L;
        final String newName = "Vagrant Story";
        final Long newYear = 2001L;

        GameDTO gameToUpdate = GameDTO.builder()
            .name(newName)
            .yearPublished(newYear)
            .build();

        //when
        ResultActions response = mockMvc.perform(
            put("/games/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(gameToUpdate))
        );

        //then
        response
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("@.id").value(id))
            .andExpect(jsonPath("@.name").value(newName))
            .andExpect(jsonPath("@.yearPublished").value(newYear))
            .andExpect(jsonPath("@.createOn").isNotEmpty());
    }

    @Test
    public void testThatUpdateAGameWithNonExistenIdShouldRetunrNotFound404() throws Exception {

        //given
        Long id = 655L;
        GameDTO someName = GameDTO.builder()
            .name("SomeName")
            .yearPublished(3500L)
            .build();

        //when
        ResultActions response = mockMvc.perform(
            put("/games/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(someName))
        );

        //then
        response
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("@.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("@.message").value("Game with id [655] not found"));

    }
}
