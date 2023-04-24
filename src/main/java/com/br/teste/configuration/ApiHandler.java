package com.br.teste.configuration;

import com.br.teste.domain.response.ExceptionResponse;
import com.br.teste.exception.DefaultApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiHandler {

    @ExceptionHandler(DefaultApiException.class)
    public ResponseEntity<ExceptionResponse> handleApiException(DefaultApiException e) {
        var response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
