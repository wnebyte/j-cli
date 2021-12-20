package com.github.wnebyte.jcli.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.github.wnebyte.jarguments.Argument;
import com.github.wnebyte.jarguments.Positional;
import com.github.wnebyte.jcli.BaseCommand;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;

public class PostTransformationFilter implements Filter<BaseCommand> {

    private final List<BaseCommand> mapped;

    public PostTransformationFilter() {
        this.mapped = new ArrayList<>();
    }

    @Override
    public boolean test(BaseCommand cmd) {
        String prefix = cmd.getPrefix();
        Set<String> names = cmd.getNames();

        if (names.isEmpty()) {
            throw new IllegalAnnotationException(
                    "A Command must consist of at least one name."
            );
        }

        // check if command names have previously been mapped.
        boolean nameCollision = mapped.stream()
                .anyMatch(c -> c.getPrefix().equals(prefix) && intersection(c.getNames(), names));

        if (nameCollision) {
            throw new IllegalAnnotationException(
                    String.format(
                            "A Command with prefix: %s and one or more of the names: %s has already been mapped.",
                            prefix, Arrays.toString(names.toArray())
                    )
            );
        }

        mapped.add(cmd);
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

    private static boolean intersection(List<Argument> args) {
        args = args.stream().filter(arg -> !(arg instanceof Positional)).collect(Collectors.toList());

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