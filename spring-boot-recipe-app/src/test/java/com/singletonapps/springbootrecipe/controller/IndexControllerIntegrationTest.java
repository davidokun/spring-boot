package com.singletonapps.springbootrecipe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@AutoConfigureMockMvc
@WebMvcTest(IndexController.class)
class IndexControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        //when
        ResultActions response = mockMvc.perform(
                get("/")
                        .contentType(MediaType.TEXT_PLAIN)
        );

        //then
        response
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(notNullValue()));
    }
}