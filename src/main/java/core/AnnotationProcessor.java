package core;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import exception.config.IllegalAnnotationException;
import static util.AnnotationUtil.*;

public final class AnnotationProcessor {

    public final Map<String, Command> process(final Set<Command> transientCommands)
            throws IllegalAnnotationException
    {
        // commands
        Map<String, Command> commands = new HashMap<>(transientCommands.size());
        // signatures
        Set<List<String>> signatures = new HashSet<>(transientCommands.size());

        for (Command command : transientCommands) {
            Method method = command.getMethod();
            method.setAccessible(true);

            if (!(isAnnotated(method))) {
                throw new IllegalAnnotationException(
                        "\t\n" + "Method " + method + " is not annotated with " + annotation.Command.class.toString() + "."
                );
            }

            // signature check
            List<List<String>> signature = signatureOf(command);
            boolean duplicate = !addSignature(signatures, signature);

            // assert that signature has not already been generated
            if (duplicate) {
                throw new IllegalAnnotationException(
                        "\t\n" + "The named properties of a Command may not collide with those of another Command.\n" +
                                 "Signature " + Arrays.toString(signature.toArray()) + " has already been generated."
                );
            }
            // assert that every non-positional argument has a distinct name
            if (!distinct(command.getArguments().stream()
                    .filter(argument -> !(argument instanceof Positional))
                    .map(Argument::getName)
                    .collect(Collectors.toList()))) {
                throw new IllegalAnnotationException(
                        "\t\n" + "The named properties of a Command must all be distinct."
                );
            }

            // build regular expression key
            StringBuilder keyBuilder = new StringBuilder();
            keyBuilder
                    .append("^")
                    .append(command.hasPrefix() ? command.getPrefix().concat("\\s") : "")
                    .append(command.getName())
                    .append(permute(command.getArguments()))
                    .append("$");

        //    System.out.println(keyBuilder.toString());

            commands.put(keyBuilder.toString(), command);
        }

        return commands;
    }

    /**
     * Checks that the signatures generated from a <code>Command</code> are all distinct from one another.
     * <p/>
     */
    @SuppressWarnings("StringEquality")
    private boolean distinct(List<String> values) {
        // iterate of the values
        for (String value : values) {
            // fetch the "not this" values
            List<String> other = values.stream()
                    .filter(s -> !(s == value))
                    .collect(Collectors.toList());
            // check for equality
            boolean taken = other.stream()
                    .anyMatch(s -> (s.equals(value)));
            // if equal return false
            if (taken) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a signature from the specified <code>Command</code>.
     * <p></p>
     */
    private List<List<String>> signatureOf(final Command command) {
        List<List<String>> sigOf = new ArrayList<>();
        List<String> signature = new ArrayList<>();

        if (command.hasPrefix()) {
            signature.add(command.getPrefix());
        }
        signature.add(command.getName());
        signature.addAll(command.getArguments().stream()
                .filter(argument -> argument instanceof Positional)
                .map(Argument::getName)
                .sorted()
                .collect(Collectors.toList()));
        signature.addAll(command.getArguments().stream()
                .filter(argument -> argument instanceof Required)
                .map(Argument::getName)
                .sorted()
                .collect(Collectors.toList()));
        Set<String> set = command.getArguments().stream()
                .filter(argument -> argument instanceof Optional)
                .map(Argument::getName)
                .collect(Collectors.toSet());

        Set<Set<String>> powerSet = Sets.powerSet(set);

        for (Set<String> s : powerSet) {
            sigOf.add(new ArrayList<>(signature) {{ addAll(s); }});
        }

        return sigOf;
    }

    private boolean addSignature(Set<List<String>> set, List<List<String>> list) {
        for (List<String> element : list) {
            boolean duplicate = !set.add(element);
            if (duplicate) {
                return false;
            }
        }
        return true;
    }

    private String permute(final List<Argument> arguments)
    {
        if ((arguments == null) || (arguments.size() == 0)) {
            return "";
        }

        String str  = "(";
        int i = 0;
        Set<List<String>> regExpressions = toSet(arguments);
        for (List<String> permutation : regExpressions)
        {
            String string = Arrays.toString(permutation.toArray());
            str = str.concat("(").concat(string.substring(1, string.length() - 1))
                    .concat(")");
            if (i < regExpressions.size() - 1) {
                str = str.concat("|");
            }
            i++;
        }
        return str.replace(", ", "")
                .concat(")");
    }

    private Set<List<String>> toSet(final List<Argument> arguments)
    {
        // init a set for all the regex-permutations
        Set<List<String>> set = new HashSet<>();
        // init a linked list for the regex's belonging to the non-positional arguments
        LinkedList<String> nonPositional = arguments.stream().filter(arg -> !(arg instanceof Positional))
                .map(Argument::getRegex)
                .collect(Collectors.toCollection(LinkedList::new));

        // arguments contain no non-positional arguments
        if (nonPositional.isEmpty()) {
            set.add(arguments.stream()
                    .map(Argument::getRegex)
                    .collect(Collectors.toList()));
            return set;
        }

        // iterate over the non-positional argument regex's
        for (int i = 0; i < nonPositional.size(); i++)
        {
            // shift the regex linked list to the left <<
            String head = nonPositional.pop();
            nonPositional.add(head);

            // init a mirror arrayList of the linked list
            List<String> list = new ArrayList<>(nonPositional);

            // iterate over the mirror list
            for (int j = 0; j < list.size(); j++) {
                if (j != 0) {
                    // swap the elements of the mirror list around
                    Collections.swap(list, j, j - 1);
                }
                // for each permutation of the mirror list, insert back the positional arguments at their indexed positions
                set.add(new ArrayList<>(list){
                    {
                        arguments.stream().filter(arg -> (arg instanceof Positional))
                                .forEach(arg -> {
                                    Positional argument = (Positional) arg;
                                    add(argument.getPosition(), argument.getRegex());
                                });
                    }
                });
            }
        }
        return set;
    }
}
