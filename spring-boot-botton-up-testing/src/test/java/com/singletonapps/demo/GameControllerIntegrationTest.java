package com.singletonapps.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singletonapps.demo.model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = BottomUpTestingApplication.class
)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:delete.sql", executionPhase = AFTER_TEST_METHOD)
public class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

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
}
