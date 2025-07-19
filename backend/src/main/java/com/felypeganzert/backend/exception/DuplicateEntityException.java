package com.felypeganzert.backend.exception;

/**
 * Exceção lançada quando se tenta criar uma entidade que já existe.
 */
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
