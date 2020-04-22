package com.singletonapps.springbootrecipe.controller;

import com.singletonapps.springbootrecipe.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    public static final String INDEX_PAGE = "index";

    @InjectMocks
    private IndexController indexController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Test
    public void shouldReturnIndexPage() {
        //when
        String indexPage = indexController.getIndexPage(model);

        //then
        assertEquals(INDEX_PAGE, indexPage);
    }
}