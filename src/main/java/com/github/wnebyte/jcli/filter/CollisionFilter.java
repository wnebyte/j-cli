package com.github.wnebyte.jcli.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jcli.BaseCommand;

public class CollisionFilter implements Filter<BaseCommand> {

    private final List<BaseCommand> processed;

    public CollisionFilter() {
        this.processed = new ArrayList<>();
    }

    @Override
    public boolean test(BaseCommand cmd) {
        Set<String> names = cmd.getNames();
        // check if command names have previously been processed.
        boolean nameCollision = processed.stream().anyMatch(c -> intersection(c.getNames(), names));

        if (nameCollision) {
            System.err.println(
                    "Command containing one or more of the name(s): " + Arrays.toString(cmd.getNames().toArray()) +
                            " has already been processed."
            );
        }

        // Todo: is caught by the factory.
        List<Argument> args = cmd.getArguments();
        // check if any command arguments have the same name(s).
        boolean argNameCollision = intersection(args);

        if (argNameCollision) {
            System.err.println(
                    "One or more of the name(s): " + Arrays.toString(toArray(args)) +
                            " appear in multiple Arguments belonging to the same Command."
            );
        }

        processed.add(cmd);
        return !nameCollision && !argNameCollision;
    }

    private static boolean intersection(Set<?> set1, Set<?> set2) {
        for (Object o : set1) {
            boolean contains = set2.contains(o);
            if (contains) {
                return true;
            }
        }
        return false;
    }

    private static boolean intersection(List<Argument> args) {
        for (int i = 0; i < args.size(); i++) {
            Argument first = args.get(i);

            for (int j = i + 1; j < args.size(); j++) {
                Argument second = args.get(j);
                boolean intersection = intersection(first.getNames(), second.getNames());
                if (intersection) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String[] toArray(List<Argument> args) {
        String[] arr = new String[args.stream().mapToInt(arg -> arg.getNames().size()).sum()];
        int i = 0;
        for (Argument arg : args) {
            for (String name : arg.getNames()) {
                arr[i++] = name;
            }
        }
        return arr;
    }
}