package com.singletonapps.springbootrecipe.controller;

import com.singletonapps.springbootrecipe.domain.Recipe;
import com.singletonapps.springbootrecipe.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final RecipeService recipeService;

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        Set<Recipe> recipes = recipeService.getRecipes();
        model.addAttribute("recipes", recipes);
        return "index";
    }
}
