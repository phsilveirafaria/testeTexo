package com.br.teste.exception;

public abstract class DefaultApiException extends RuntimeException {

    public DefaultApiException(String message) {
        super(message);
    }
}