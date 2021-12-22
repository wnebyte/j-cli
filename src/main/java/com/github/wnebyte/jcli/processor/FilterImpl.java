package com.github.wnebyte.jcli.processor;

import java.util.*;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jcli.BaseCommand;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;

public class FilterImpl implements Filter<BaseCommand> {

    private final List<BaseCommand> processed;

    public FilterImpl() {
        this.processed = new ArrayList<>();
    }

    @Override
    public boolean test(BaseCommand cmd) {
        String prefix = cmd.getPrefix();
        Set<String> names = cmd.getNames();

        if (names.isEmpty()) {
            throw new IllegalAnnotationException(
                    String.format(
                            "Command: '%s' must have at least one name.", cmd
                    )
            );
        }

        // check if names have previously been mapped.
        boolean nameCollision = processed.stream()
                .anyMatch(c -> c.getPrefix().equals(prefix) && intersection(c.getNames(), names));

        if (nameCollision) {
            throw new IllegalAnnotationException(
                    String.format(
                            "A Command with prefix: %s and one or more of the names: %s has already been mapped.",
                            prefix, Arrays.toString(names.toArray())
                    )
            );
        }

        if (cmd.hasPrefix()) {
            // check if prefix collides with any of the names.
            nameCollision = names.contains(prefix);
            if (nameCollision) {
                throw new IllegalAnnotationException(
                        String.format(
                                "Command: '%s' has a duplicate prefix/name.", cmd
                        )
                );
            }
            // check if prefix collides with any of the previously mapped names.
            nameCollision = processed.stream()
                    .anyMatch(c -> c.getNames().contains(prefix));
            if (nameCollision) {
                throw new IllegalAnnotationException(
                        String.format(
                                "Command: '%s' has a prefix that has already been mapped as a name.", cmd
                        )
                );
            }
        }

        processed.add(cmd);
        return true;
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