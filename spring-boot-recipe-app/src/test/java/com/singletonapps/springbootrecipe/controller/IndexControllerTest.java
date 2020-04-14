package com.singletonapps.springbootrecipe.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexControllerTest {

    public static final String INDEX_PAGE = "index";

    @Test
    public void shouldReturnIndexPage() {
        //given
        IndexController indexController = new IndexController();

        //when
        String indexPage = indexController.getIndexPage();

        //then
        assertEquals(INDEX_PAGE, indexPage);
    }
}