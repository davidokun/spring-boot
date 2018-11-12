package com.singletonapps.demo.controller.handler;
import com.singletonapps.demo.common.ErrorResponseMessage;
import com.singletonapps.demo.exception.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GameControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponseMessage> handlerGameNotFound(GameNotFoundException e) {

        ErrorResponseMessage body = ErrorResponseMessage.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message(e.getMessage())
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
