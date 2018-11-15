package com.singletonapps.demo.controller.impl;

import com.singletonapps.demo.controller.GameController;
import com.singletonapps.demo.dto.GameDTO;
import com.singletonapps.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameControllerImpl implements GameController {

    @Autowired
    private GameService gameService;

    @Override
    public GameDTO createGame(@RequestBody final GameDTO game) {
        return gameService.createGame(game);
    }

    @Override
    public GameDTO getGameById(@PathVariable Long id) {
        return gameService.findGameById(id);
    }

    @Override
    public List<GameDTO> getAllGames() {
        return gameService.findAllGames();
    }

    @Override
    public GameDTO updateGame(final Long id, final GameDTO game) {
        return gameService.updateGame(id, game);
    }
}
