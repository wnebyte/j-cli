package com.github.wnebyte.jcli.processor;

import java.util.*;
import com.github.wnebyte.jcli.BaseCommand;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;
import com.github.wnebyte.jcli.util.Sets;

public class FilterImpl implements Filter<BaseCommand> {

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    private final List<BaseCommand> commands;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public FilterImpl() {
        this.commands = new ArrayList<>();
    }

    /*
    ###########################
    #          METHODS        #
    ###########################
    */

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
        boolean nameCollision = commands.stream()
                .anyMatch(c -> c.getPrefix().equals(prefix) && Sets.intersects(c.getNames(), names));

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
            nameCollision = commands.stream()
                    .anyMatch(c -> c.getNames().contains(prefix));
            if (nameCollision) {
                throw new IllegalAnnotationException(
                        String.format(
                                "Command: '%s' has a prefix that has already been mapped as a name.", cmd
                        )
                );
            }
        }

        commands.add(cmd);
        return true;
    }
}