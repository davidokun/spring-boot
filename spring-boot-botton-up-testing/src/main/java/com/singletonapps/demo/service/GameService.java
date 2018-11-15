package com.singletonapps.demo.service;

import com.singletonapps.demo.dto.GameDTO;

import java.util.List;

public interface GameService {

    GameDTO createGame(GameDTO game);

    List<GameDTO> findAllGames();

    GameDTO findGameById(Long id);

    GameDTO updateGame(Long id, GameDTO gameToUpdate);
}
