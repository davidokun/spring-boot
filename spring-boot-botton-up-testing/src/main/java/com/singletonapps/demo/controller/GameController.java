package com.singletonapps.demo.controller;

import com.singletonapps.demo.dto.GameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

public interface GameController {

    @PostMapping(
        value = "/games",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    GameDTO createGame(@RequestBody final GameDTO game);

    @GetMapping(
        value = "/games/{id}",
        produces = APPLICATION_JSON_UTF8_VALUE)
    GameDTO getGameById(@PathVariable Long id);
}

