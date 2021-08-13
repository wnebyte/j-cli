package com.github.wnebyte.jshell.exception.runtime;

import com.github.wnebyte.jshell.Argument;
import com.github.wnebyte.jshell.Command;

public final class ParseException extends RuntimeException {

    private Command command;

    private Argument argument;

    private String input;

    private String value;

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, String value) {
        this(message);
        setValue(value);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setArgument(Argument argument) {
        this.argument = argument;
    }

    public void setInput(String input) {
        this.input = input;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public Command getCommand() {
        return command;
    }

    public String getInput() {
        return input;
    }

    public Argument getArgument() {
        return argument;
    }

    public String getValue() {
        return value;
    }
}
