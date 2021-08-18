package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.runtime.ParseException;
import com.github.wnebyte.jshell.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.wnebyte.jshell.util.StringUtil.splitByWhitespace;

/**
 * This package-private class declares methods for parsing input into a ready-to-be-invoked Command.
 */
final class Parser {

    private final Command command;

    private final String input;

    public Parser(Command command, String input) {
        this.command = command;
        this.input = input;
    }

    /**
     * Parses the specified input and Command-Arguments into an array of initialized Objects.
     * @return array of initialized Objects.
     * @throws ParseException if one of the Arguments could not be converted into the desired type,
     * or if the structure of the input is invalid.
     */
    /*
    should only be called by the specified Command with the right input.
     */
    Object[] parse() throws ParseException {
        Object[] args = new Object[command.getMethod().getParameterCount()]; // array to be returned
        List<Argument> arguments = new ArrayList<>(command.getArguments()); // list of arguments
        if (arguments.size() == 0) {
            return args;
        }
        List<Positional> positionalArguments = // list of positional arguments
                arguments.stream().filter(arg -> arg instanceof Positional)
                        .map(argument -> (Positional) argument)
                        .collect(Collectors.toList());
        LinkedList<String> values = process(splitByWhitespace(input)); // list of user input values

        /*
        iterate and initialize the positional arguments
         */
        for (Positional argument : positionalArguments) {
            String value = values.pop();
            arguments.remove(argument);
            try {
                args[argument.getIndex()] = argument.initialize(value);
            } catch (ParseException e) {
                e.setCommand(command);
                e.setArgument(argument);
                e.setInput(input);
                throw e;
            }
        }
        /*
        iterate the remaining input values and grep the argument whose name equals the value,
        and initialize it.
         */
        for (int i = 0; i < values.size(); i++) {
            final String value = values.get(i);
            String val;
            Argument argument = arguments.stream()
                    .filter(arg -> arg.getName().equals(value))
                    .findFirst()
                    .orElseThrow(() -> new ParseException("A logical error has occurred."));
            val = value.concat((i + 1) < values.size() ?
                    " ".concat(values.get(i + 1)) : "");
            i++;
            arguments.remove(argument);
            try {
                args[argument.getIndex()] = argument.initialize(val);
            } catch (ParseException e) {
                e.setCommand(command);
                e.setArgument(argument);
                e.setInput(input);
                throw e;
            }
        }
        /*
        iterate any remaining arguments, and if they are optional initialize them with default values.
         */
        for (Argument argument : arguments) {
            if (argument instanceof Optional) {
                try {
                    args[argument.getIndex()] = argument.initialize("");
                } catch (ParseException e) {
                    e.setCommand(command);
                    e.setArgument(argument);
                    e.setInput(input);
                    throw e;
                }
            }
        }

        return args;
    }

    /**
     * Pre-process the split user input by removing the indices corresponding to the Command prefix & name.
     * @param values the split user input.
     * @return the processed user input.
     */
    private LinkedList<String> process(final List<String> values) {
        if (command.hasPrefix()) {
            values.remove(0);
        }
        values.remove(0);
        return new LinkedList<>(values);
    }

    /**
     * Splits the specified <code>input</code> into a List of "words".
     * @see StringUtil#splitByWhitespace(String)
     */
    static List<String> split(final String input) {
        return StringUtil.splitByWhitespace(input);
    }
}
