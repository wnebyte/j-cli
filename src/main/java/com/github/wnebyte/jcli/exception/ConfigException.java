package com.github.wnebyte.jcli.exception;

/*
extends RuntimeException so that it can be thrown inside of a lambda expression.
 */
public class ConfigException extends RuntimeException {

    public ConfigException(String msg) {
        super(msg);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }
}
