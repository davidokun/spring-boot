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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
}
