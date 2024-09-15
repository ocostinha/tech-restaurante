package com.fiap.tech.restaurante.exception;

public class UnavailableSeatsException extends RuntimeException {
    public UnavailableSeatsException(String message) {
        super(message);
    }
}
