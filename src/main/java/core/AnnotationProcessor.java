package core;

import exception.config.IllegalAnnotationException;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static util.AnnotationUtil.*;

public final class AnnotationProcessor {

    public final Map<String, Command> process(final Set<Command> transientCommands)
            throws IllegalAnnotationException
    {
        Map<String, Command> commands = new HashMap<>(transientCommands.size());
        Set<List<String>> distinct = new HashSet<>(transientCommands.size());

        for (Command command : transientCommands) {
            Method method = command.getMethod();
            method.setAccessible(true);

            if (!(isAnnotated(method))) {
                throw new IllegalAnnotationException(
                        "Command Annotation needs to be present if a method is to be processed as a Command."
                );
            }

            // signature check
            List<String> signature = signature(command);
            boolean unique = distinct.add(signature);

            // assert that signature has not already been produced
            if (!unique) {
                throw new IllegalAnnotationException(
                        "\t\nThe named properties of a Command -- not including the name of any optional Arguments may not collide with that" +
                                " of another Command.\n\t" +
                                "Command signature: "+ Arrays.toString(signature.toArray()) + " has already been mapped."
                );
            }

            // assert that every non-positional argument has a distinct name/signature value
            if (!distinct(signature)) {
                throw new IllegalAnnotationException(
                        "\n\tThe named attributes of a Command must be distinct, " +
                                "and may not exists as substring(s) elsewhere."
                        + "\n\t" + command.toString()
                );
            }

            // build key
            StringBuilder keyBuilder = new StringBuilder();
            keyBuilder
                    .append("^")
                    .append(command.hasPrefix() ? command.getPrefix().concat("\\s") : "")
                    .append(command.getName())
                    .append(permute(command.getArguments()))
                    .append("$");

            System.out.println(keyBuilder.toString());

            commands.put(keyBuilder.toString(), command);
        }

        return commands;
    }

    /**
     * Checks that the signatures generated from a <code>Command</code> are all distinct from one another.
     * <p/>
     */
    private boolean distinct(List<String> signature) {
        signature = new ArrayList<>(signature); // the signature of the command
        signature.removeIf(s -> s.equals(Positional.SYMBOL)); // remove signature generated from positional-argument

        // iterate the signature values
        for (String value : signature) {
            // fetch the 'other' signature values
            List<String> other = signature.stream()
                    .filter(s -> !(s.equals(value))).collect(Collectors.toList());
            // check for equality
            boolean taken = other.stream()
                    .anyMatch(s -> (s.equals(value) || (s.contains(value)) || (value.contains(s))));
            // if equal return false
            if (taken) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a signature from the <code>Command</code>.
     * <p></p>
     */
    private List<String> signature(Command command) {
        List<String> signature = command.getArguments().stream()
                .filter(arg -> (arg instanceof Positional))
                .map(Argument::getName).collect(Collectors.toList());
        signature.addAll(
                command.getArguments().stream()
                        .filter(arg -> (arg instanceof Required))
                        .map(Argument::getName).sorted().collect(Collectors.toList())
        );
        int i = 0;
        if (command.hasPrefix()) {
            i = 1;
            signature.add(0, command.getPrefix());
        }
        signature.add(i, command.getName());
        return signature;
    }

    private String permute(final List<Argument> arguments)
    {
        if ((arguments == null) || (arguments.size() == 0)) {
            return "";
        }

        String str  = "(";
        int i = 0;
        for (List<String> list : toSet(arguments))
        {
          //  str = str.concat("(" + Arrays.toString(list.toArray()) + ")");
            String listAsString = Arrays.toString(list.toArray());
            str = str.concat("(").concat(listAsString.substring(1, listAsString.length() - 1))
                    .concat(")");
            if (i++ != arguments.size() - 1) {
                str = str.concat("|");
            }
        }
        return str.replace(", ", "").concat(")");
        /*
        return str
                .replace("([", "(")
                .replace("])", ")")
                .replace(", ", "")
                .concat(")");
         */
        /*
        return str
                .replace("[", "")
                .replace("]", "")
                .replace(", ", "")
                .concat(")");

         */
    }

    private Set<List<String>> toSet(final List<Argument> arguments)
    {
        // init a set for all the regex-permutations
        Set<List<String>> set = new HashSet<>();
        // init a linked list for the regex's belonging to the non-positional arguments
        LinkedList<String> linkedList = arguments.stream().filter(arg -> !(arg instanceof Positional))
                .map(Argument::getRegex).collect(Collectors.toCollection(LinkedList::new));

        // arguments contain no non-positional arguments
        if (linkedList.isEmpty()) {
            set.add(arguments.stream().map(Argument::getRegex).collect(Collectors.toList()));
            return set;
        }

        // iterate over the non-positional argument regex's
        for (int i = 0; i < linkedList.size(); i++)
        {
            // shift the regex linked list to the left <<
            String head = linkedList.pop();
            linkedList.add(head);

            // init a mirror arrayList of the linked list
            List<String> list = new ArrayList<>(linkedList);

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
                                    add(argument.getIndex(), argument.getRegex());
                                });
                    }
                });
            }
        }

        return set;
    }
}
