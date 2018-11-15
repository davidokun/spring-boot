package com.singletonapps.demo.service;

import com.singletonapps.demo.dto.GameDTO;
import com.singletonapps.demo.exception.GameNotFoundException;
import com.singletonapps.demo.model.Game;
import com.singletonapps.demo.repository.GameRepository;
import com.singletonapps.demo.service.impl.GameServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private GameRepository gameRepository;

    @Captor
    private ArgumentCaptor<Game> gameCaptor;

    @Test
    public void testInstantiateWithNoArgsConstructor() {

        //given
        GameServiceImpl gameServiceOther;

        //when
        gameServiceOther = new GameServiceImpl();

        //then
        assertThat(gameServiceOther).isNotNull();
        assertThat(gameServiceOther).isExactlyInstanceOf(GameServiceImpl.class);

    }

    @Test
    public void testGameSetTimeCreateOn() {

        //given
        String name = "Final Fantasy";
        long yearPublished = 1983L;

        GameDTO gameReceived = GameDTO.builder()
            .id(null)
            .name(name)
            .yearPublished(yearPublished)
            .build();

        LocalDateTime now = LocalDateTime.now();

        Game gameToSaved = Game.builder()
            .id(1L)
            .name(name)
            .yearPublished(yearPublished)
            .createOn(now)
            .build();

        given(gameRepository.save(any(Game.class))).willReturn(gameToSaved);

        //when
        GameDTO gameCreated = gameService.createGame(gameReceived);

        //then
        verify(gameRepository).save(gameCaptor.capture());

        assertThat(gameCaptor.getValue().getId()).isNull();
        assertThat(gameCaptor.getValue().getCreateOn()).isNotNull();
        assertThat(gameCaptor.getValue().getName()).isEqualTo(name);
        assertThat(gameCaptor.getValue().getYearPublished()).isEqualTo(yearPublished);

        assertThat(gameCreated.getId()).isGreaterThan(0);
        assertThat(gameCreated.getName()).isEqualTo(name);
        assertThat(gameCreated.getCreateOn()).isNotNull();
        assertThat(gameCreated.getCreateOn()).isEqualTo(now);
    }

    @Test
    public void testShouldFindGameById() {

        //given
        Long id = 1L;
        String name = "Final Fantasy";
        long yearPublished = 1983L;

        Game game = Game.builder()
            .id(id)
            .name(name)
            .yearPublished(yearPublished)
            .build();

        given(gameRepository.findById(id)).willReturn(Optional.of(game));

        //when
        GameDTO byId = gameService.findGameById(id);

        //then
        assertThat(byId.getId()).isEqualTo(1);
        assertThat(byId.getName()).isEqualTo(name);
        assertThat(byId.getYearPublished()).isEqualTo(yearPublished);

    }

    @Test(expected = GameNotFoundException.class)
    public void testShouldThrowGameNotFoundException() {

        //given
        Long id = 0L;

        given(gameRepository.findById(id)).willReturn(Optional.empty());

        //when
        gameService.findGameById(id);

    }

    @Test
    public void testShouldFindAllGames() {

        //given
        List<Game> gamesRetrieved = new ArrayList<>();

        Game zelda = Game.builder()
            .id(1L)
            .name("Zelda")
            .yearPublished(1998L)
            .createOn(LocalDateTime.now())
            .build();

        Game kirby = Game.builder()
            .id(2L)
            .name("Kirby")
            .yearPublished(1983L)
            .createOn(LocalDateTime.now())
            .build();

        Game mario = Game.builder()
            .id(2L)
            .name("Mario Bros")
            .yearPublished(1985L)
            .createOn(LocalDateTime.now())
            .build();

        gamesRetrieved.add(zelda);
        gamesRetrieved.add(kirby);
        gamesRetrieved.add(mario);

        given(gameRepository.findAll()).willReturn(gamesRetrieved);

        //when
        List<GameDTO> games = gameService.findAllGames();

        //then
        assertThat(games.size()).isEqualTo(3);
        assertThat(games.get(0).getId()).isGreaterThan(0);
        assertThat(games.get(0).getName()).isEqualTo("Zelda");
        assertThat(games.get(0).getYearPublished()).isEqualTo(1998);
        assertThat(games.get(0).getCreateOn()).isNotNull();

        assertThat(games.get(0).getId()).isGreaterThan(0);
        assertThat(games.get(1).getName()).isEqualTo("Kirby");
        assertThat(games.get(1).getYearPublished()).isEqualTo(1983);
        assertThat(games.get(1).getCreateOn()).isNotNull();

        assertThat(games.get(0).getId()).isGreaterThan(0);
        assertThat(games.get(2).getName()).isEqualTo("Mario Bros");
        assertThat(games.get(2).getYearPublished()).isEqualTo(1985);
        assertThat(games.get(2).getCreateOn()).isNotNull();
    }

    @Test
    public void testGivenAnExistingGameShouldBeUpdated() {

        //given
        Long id = 1L;
        final String newName = "Cybernator";
        final Long newYear = 1993L;

        GameDTO gameToUpdate = GameDTO.builder()
            .id(id)
            .name(newName)
            .yearPublished(newYear)
            .build();

        Game currentGame = Game.builder()
            .id(id)
            .name("Zelda")
            .yearPublished(1998L)
            .build();

        Game newGame = Game.builder()
            .id(id)
            .name(newName)
            .yearPublished(newYear)
            .createOn(LocalDateTime.now())
            .build();

        given(gameRepository.findById(id)).willReturn(Optional.of(currentGame));
        given(gameRepository.save(anyObject())).willReturn(newGame);

        //when
        GameDTO updatedGame = gameService.updateGame(id, gameToUpdate);

        //then
        assertThat(updatedGame.getName()).isEqualTo(newName);
        assertThat(updatedGame.getYearPublished()).isEqualTo(newYear);
        assertThat(updatedGame.getCreateOn()).isNotNull();

    }

    @Test(expected = GameNotFoundException.class)
    public void testGivenAGameIdThatDontExistsThrowGameNotFoundException() {

        //given
        Long id = 501L;
        GameDTO gameToUpdate = GameDTO.builder()
            .id(id)
            .build();

        //when
        gameService.updateGame(id, gameToUpdate);

    }
}
