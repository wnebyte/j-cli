package com.github.wnebyte.jcli;

import java.lang.reflect.Method;
import java.util.regex.Pattern;
import com.github.wnebyte.jarguments.factory.ArgumentFactoryBuilder;
import com.github.wnebyte.jcli.util.Identifier;
import com.github.wnebyte.jcli.val.CommandValidator;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class BaseTestClass {

    protected boolean allMatch(Pattern pattern, String... input) {
        for (String s : input) {
            boolean matches = pattern.matcher(s).matches();
            if (!matches) {
                System.err.printf(
                        "Input: '%s' does not match the given pattern.%n", s
                );
                return false;
            }
        }
        return true;
    }

    protected boolean allMatch(BaseCommand cmd, String... input) {
        CommandValidator validator = new CommandValidator(cmd);
        for (String s : input) {
            boolean matches = validator.validate(s);
            if (!matches) {
                System.err.printf(
                        "Input: '%s' does not match the given arguments.%n", s
                );
                return false;
            }
        }
        return true;
    }

    protected boolean noneMatch(Pattern pattern, String... input) {
        for (String s : input) {
            boolean matches = pattern.matcher(s).matches();
            if (matches) {
                System.err.printf(
                        "Input: '%s' does match the given pattern.%n", s
                );
                return false;
            }
        }
        return true;
    }

    protected boolean noneMatch(BaseCommand cmd, String... input) {
        CommandValidator validator = new CommandValidator(cmd);
        for (String s : input) {
            boolean matches = validator.validate(s);
            if (matches) {
                System.err.printf(
                        "Input: '%s' does match the given arguments.%n", s
                );
                return false;
            }
        }
        return true;
    }

    protected BaseCommand newInstance(Object object, String cmdName) {
        Method method = new Identifier(object.getClass(), cmdName).getMethod();
        assert method != null;
        return new com.github.wnebyte.jcli.Command(
                () -> object,
                method,
                new ArgumentFactoryBuilder().build()
        );
    }
}
