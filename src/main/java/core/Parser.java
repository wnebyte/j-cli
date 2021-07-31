package core;

import exception.runtime.ParseException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static util.StringUtil.split;

public final class Parser {

    private final Command command;

    private final String input;

    public Parser(Command command, String input) {
        this.command = command;
        this.input = input;
    }

    Object[] parse() throws ParseException {
        Object[] args = new Object[command.getMethod().getParameterCount()]; // array to be returned
        List<Argument> arguments = new ArrayList<>(command.getArguments()); // list of arguments
        if (arguments.size() == 0) {
            return args;
        }
        List<Positional> positionalArguments = // list of positional arguments
                arguments.stream().filter(arg -> arg instanceof Positional)
                        .map(new Function<Argument, Positional>() {
                            @Override
                            public Positional apply(Argument argument) {
                                return (Positional) argument;
                            }
                        })
                        .collect(Collectors.toList());
        LinkedList<String> values = process(split(input)); // list of user input

        for (Positional argument : positionalArguments) {
            String value = values.pop();
            arguments.remove(argument);
            try {
                args[argument.getIndex()] = argument.initialize(value);
            } catch (ParseException e) {
                e.setCommand(command);
                e.setArgument(argument);
                e.setUserInput(input);
                throw e;
            }
        }
        for (int i = 0; i < values.size(); i++) {
            final String value = values.get(i);
            String val;
            Argument argument = arguments.stream().filter(arg -> arg.getName().equals(value)).findFirst()
                    .orElseThrow();
            val = value.concat((i + 1) < values.size() ?
                    " ".concat(values.get(i + 1)) :
                    "");
            i++;
            arguments.remove(argument);
            try {
                args[argument.getIndex()] = argument.initialize(val);
            } catch (ParseException e) {
                e.setCommand(command);
                e.setArgument(argument);
                e.setUserInput(input);
                throw e;
            }
        }
        for (Argument argument : arguments) {
            if (argument instanceof Optional) {
                try {
                    args[argument.getIndex()] = argument.initialize("");
                } catch (ParseException e) {
                    e.setCommand(command);
                    e.setArgument(argument);
                    e.setUserInput(input);
                    throw e;
                }
            }
        }

        return args;
    }

    private LinkedList<String> process(List<String> values) {
        if (command.hasPrefix()) {
            values.remove(0);
        }
        values.remove(0);
        return new LinkedList<>(values);
    }
}
