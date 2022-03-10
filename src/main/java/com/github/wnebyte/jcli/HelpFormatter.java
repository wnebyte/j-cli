package com.github.wnebyte.jcli;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.util.Chars;
import com.github.wnebyte.jarguments.util.Strings;
import com.github.wnebyte.jarguments.util.Objects;

public class HelpFormatter implements Formatter<BaseCommand> {

    /*
    ###########################
    #     UTILITY METHODS     #
    ###########################
    */

    private static int maxLength(Collection<Argument> c) {
        return c.stream().map(ARGUMENT_FORMATTER)
                .max(Comparator.comparingInt(String::length))
                .orElse(Strings.EMPTY)
                .length();
    }

    private static int maxTotalLength(Collection<Argument> c) {
        int max = maxLength(c);

        return c.stream().map(new Function<Argument, String>() {
            @Override
            public String apply(Argument argument) {
                String s = ARGUMENT_FORMATTER.apply(argument);
                String indent = indent((max + 1) - s.length());
                StringBuilder out = new StringBuilder();
                out.append(Strings.WHITESPACE).append(s).append(indent)
                        .append(Objects.requireNonNullElseGet(argument.getDesc(), () -> Strings.EMPTY));
                return out.toString();
            }
        }).max(Comparator.comparingInt(String::length))
                .orElse(Strings.EMPTY)
                .length();
    }

    private static String indent(int len) {
        char[] arr = new char[len];
        Arrays.fill(arr, Chars.WHITESPACE);
        return new String(arr);
    }

    /*
    ###########################
    #      STATIC FIELDS      #
    ###########################
    */

    private static final Formatter<BaseCommand> COMMAND_FORMATTER = BaseCommand::toPaddedDescriptiveString;

    private static final Formatter<Argument> ARGUMENT_FORMATTER = Argument::toPaddedString;

    /*
    ###########################
    #         METHODS         #
    ###########################
    */

    @Override
    public String apply(BaseCommand cmd) {
        StringBuilder out = new StringBuilder();
        out.append(cmd.hasDesc() ? cmd.getDesc().concat("\n") : Strings.EMPTY)
                .append("Usage: ").append(COMMAND_FORMATTER.apply(cmd));

        if (!cmd.getArguments().isEmpty()) {
            int maxLength  = maxLength(cmd.getArguments());
           // int maxTotalLength = maxTotalLength(cmd.getArguments());
            out.append("\n\n")
                    .append("OPTIONS: ")
                    .append("\n");

            for (Argument argument : cmd.getArguments()) {
                String s = ARGUMENT_FORMATTER.apply(argument);
                String indent = indent((maxLength + 1) - s.length());
                String ln = new StringBuilder().append("\t").append(s).append(indent)
                        .append(Objects.requireNonNullElseGet(argument.getDesc(), () -> Strings.EMPTY)).toString();
                out.append(ln);
                /*
                indent = indent((maxTotalLength + 1) - ln.length());
                out.append(indent).append("\n");
                 */
                out.append("\n");
            }
        } else {
            out.append("\n");
        }

        return out.toString();
    }
}
