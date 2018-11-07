package com.singletonapps.demo.dto;

import com.singletonapps.demo.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    GameDTO gameToGameDto(Game game);

    Game gameDtoToGame(GameDTO game);
}
