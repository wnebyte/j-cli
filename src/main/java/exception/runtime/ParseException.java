package exception.runtime;

import core.Argument;
import core.Command;

public final class ParseException extends RuntimeException {

    private Command command;

    private Argument argument;

    private String userInput;

    private String raw;

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, String raw) {
        this(message);
        setRaw(raw);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setArgument(Argument argument) {
        this.argument = argument;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }


    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Command getCommand() {
        return command;
    }

    public String getUserInput() {
        return userInput;
    }

    public Argument getArgument() {
        return argument;
    }

    public String getRaw() {
        return raw;
    }
}
