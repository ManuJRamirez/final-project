package com.wallaclone.finalproject.exceptions;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String mensaje) {
        super(mensaje);
    }
}
