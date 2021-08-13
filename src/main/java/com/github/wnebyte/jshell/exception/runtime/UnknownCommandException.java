package com.github.wnebyte.jshell.exception.runtime;

public final class UnknownCommandException extends RuntimeException {

    public UnknownCommandException() {

    }

    public UnknownCommandException(String message) {
        super(message);
    }
}
