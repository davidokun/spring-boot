package com.singletonapps.demo.service.impl;
import com.singletonapps.demo.dto.GameDTO;
import com.singletonapps.demo.dto.GameMapper;
import com.singletonapps.demo.model.Game;
import com.singletonapps.demo.repository.GameRepository;
import com.singletonapps.demo.service.GameService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    @Override
    public GameDTO createGame(GameDTO gameDTO) {

        LocalDateTime now = LocalDateTime.now();

        Game newGame = Game.builder()
            .name(gameDTO.getName())
            .yearPublished(gameDTO.getYearPublished())
            .createOn(now)
            .build();

        Game saved = gameRepository.save(newGame);

        return GameMapper.INSTANCE
            .gameToGameDto(saved);

    }
}
