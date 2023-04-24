package com.br.teste.domain.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionResponse {

    private HttpStatus status;
    private String message;
}