package com.singletonapps.demo.controller.impl;

import com.singletonapps.demo.controller.GameController;
import com.singletonapps.demo.dto.GameDTO;
import com.singletonapps.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameControllerImpl implements GameController {

    @Autowired
    private GameService gameService;

    @Override
    public GameDTO createGame(@RequestBody final GameDTO game) {
        return gameService.createGame(game);
    }
}
