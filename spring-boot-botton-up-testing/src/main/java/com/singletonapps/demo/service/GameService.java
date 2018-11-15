package com.singletonapps.demo.service;

import com.singletonapps.demo.dto.GameDTO;

import java.util.List;

public interface GameService {

    GameDTO createGame(final GameDTO game);

    List<GameDTO> findAllGames();

    GameDTO findGameById(final Long id);

    GameDTO updateGame(final Long id, final GameDTO gameToUpdate);
}
