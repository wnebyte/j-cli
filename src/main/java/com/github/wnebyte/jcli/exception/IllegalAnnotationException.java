package com.github.wnebyte.jcli.exception;

public class IllegalAnnotationException extends ConfigException {

    public IllegalAnnotationException(String msg) {
        super(msg);
    }

    public IllegalAnnotationException(Throwable cause) {
        super(cause);
    }
}
