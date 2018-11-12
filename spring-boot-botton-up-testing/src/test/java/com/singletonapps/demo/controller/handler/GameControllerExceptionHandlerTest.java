package com.singletonapps.demo.controller.handler;

import com.singletonapps.demo.common.ErrorResponseMessage;
import com.singletonapps.demo.exception.GameNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GameControllerExceptionHandlerTest {

    private GameControllerExceptionHandler handler;


    @Before
    public void setUp() {
        handler = new GameControllerExceptionHandler();
    }

    @Test
    public void testReturnNotFoundExceptionMessage() {

        //given
        String message = "Game Not Found";
        GameNotFoundException not_found = new GameNotFoundException(message);

        //when
        ResponseEntity<ErrorResponseMessage> responseEntity =
            handler.handlerGameNotFound(not_found);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(message);

    }
}
