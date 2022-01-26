package com.github.wnebyte.jcli.exception;

public class UnknownCommandException extends RuntimeException {

    private final String input;

    public UnknownCommandException(String msg, String input) {
        super(msg);
        this.input = input;
    }

    public UnknownCommandException(Throwable cause, String input) {
        super(cause);
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
