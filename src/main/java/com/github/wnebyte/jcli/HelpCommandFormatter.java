package com.github.wnebyte.jcli;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import com.github.wnebyte.jarguments.Argument;

public class HelpCommandFormatter implements Formatter<BaseCommand> {

    private static final Function<BaseCommand, String> CMD_TO_STRING_FUNCTION = BaseCommand::toPaddedString;

    private static final Function<Argument, String> ARG_TO_STRING_FUNCTION = Argument::toPaddedString;

    @Override
    public String apply(BaseCommand cmd) {
        StringBuilder out = new StringBuilder();
        out.append(cmd.hasDescription() ? cmd.getDescription().concat("\n") : "")
                .append("Usage: ").append(CMD_TO_STRING_FUNCTION.apply(cmd));

        if (!cmd.getArguments().isEmpty()) {
            int maxLength  = maxToStringLength(cmd.getArguments());
            int maxTotalLength = maxTotalLength(cmd.getArguments());
            out.append("\n\n").append("Arguments: ").append("\n");

            for (Argument arg : cmd.getArguments()) {
                String s = ARG_TO_STRING_FUNCTION.apply(arg);
                String indent = indent((maxLength + 1) - s.length());
                String ln = new StringBuilder().append(" ").append(s).append(indent)
                        .append(arg.getDescription()).toString();
                out.append(ln);
                indent = indent((maxTotalLength + 1) - ln.length());
                out.append(indent).append("<").append(arg.getType().getSimpleName()).append(">").append("\n");
            }
        } else {
            out.append("\n");
        }

        return out.toString();
    }

    private int maxToStringLength(final List<Argument> args) {
        return args.stream().map(ARG_TO_STRING_FUNCTION).max(Comparator.comparingInt(String::length))
                .orElse("")
                .length();
    }

    private int maxTotalLength(final List<Argument> args) {
        int max = maxToStringLength(args);

        return args.stream().map(new Function<Argument, String>() {
            @Override
            public String apply(Argument arg) {
                String s = ARG_TO_STRING_FUNCTION.apply(arg);
                String indent = indent((max + 1) - s.length());
                StringBuilder out = new StringBuilder();
                out.append(" ").append(s).append(indent).append(arg.getDescription());
                return out.toString();
            }
        }).max(Comparator.comparingInt(String::length)).orElse("").length();
    }

    private String indent(int len) {
        char[] arr = new char[len];
        Arrays.fill(arr, ' ');
        return new String(arr);
    }
}
