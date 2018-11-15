package com.singletonapps.demo.controller;

import com.singletonapps.demo.dto.GameDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RequestMapping("/games")
public interface GameController {

    @PostMapping(
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    @ApiOperation("Create a new Game")
    GameDTO createGame(@Valid @RequestBody final GameDTO game);

    @GetMapping(
        value = "/{id}",
        produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(OK)
    @ApiOperation("Get a Game by Id")
    GameDTO getGameById(@PathVariable final Long id);

    @GetMapping(
        produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(OK)
    @ApiOperation("Get all Existing Games")
    List<GameDTO> getAllGames();

    @PutMapping(
        value = "/{id}",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(OK)
    @ApiOperation("Update an Existing Game")
    GameDTO updateGame(@PathVariable final Long id, @Valid @RequestBody final GameDTO game);
}

