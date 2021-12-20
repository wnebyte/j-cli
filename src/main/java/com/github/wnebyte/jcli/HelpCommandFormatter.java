package com.github.wnebyte.jcli;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import com.github.wnebyte.jarguments.Argument;

public class HelpCommandFormatter implements Formatter<BaseCommand> {

    private static final Formatter<BaseCommand> CMD_TO_STRING_FUNCTION = BaseCommand::toPaddedDescriptiveString;

    private static final Formatter<Argument> ARG_TO_STRING_FUNCTION = Argument::toGenericString;

    @Override
    public String apply(BaseCommand cmd) {
        StringBuilder out = new StringBuilder();
        out.append(cmd.hasDesc() ? cmd.getDesc().concat("\n") : "")
                .append("Usage: ").append(CMD_TO_STRING_FUNCTION.apply(cmd));

        if (!cmd.getArguments().isEmpty()) {
            int maxLength  = maxToStringLength(cmd.getArguments());
            int maxTotalLength = maxTotalLength(cmd.getArguments());
            out.append("\n\n").append("OPTIONS: ").append("\n");

            for (Argument arg : cmd.getArguments()) {
                String s = ARG_TO_STRING_FUNCTION.apply(arg);
                String indent = indent((maxLength + 1) - s.length());
                String ln = new StringBuilder().append("\t").append(s).append(indent)
                        .append(arg.getDesc()).toString();
                out.append(ln);
                indent = indent((maxTotalLength + 1) - ln.length());
                out.append(indent).append("\n");
               // out.append(indent).append("<").append(arg.getType().getSimpleName()).append(">").append("\n");
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
                out.append(" ").append(s).append(indent).append(arg.getDesc());
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
