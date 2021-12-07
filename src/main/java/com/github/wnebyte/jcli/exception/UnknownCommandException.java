package com.github.wnebyte.jcli.exception;

public class UnknownCommandException extends RuntimeException {

    public UnknownCommandException(String msg) {
        super(msg);
    }
}
