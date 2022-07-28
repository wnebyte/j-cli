package com.github.wnebyte.jcli.util;

import java.lang.reflect.Method;
import java.util.Set;

public class CommandIdentifier {

    private final Class<?> cls;

    private final String cmdName;

    public CommandIdentifier(Class<?> cls, String cmdName) {
        this.cls = cls;
        this.cmdName = cmdName;
    }

    public Method getMethod() {
        for (Method method : cls.getDeclaredMethods()) {
            Set<String> names = Annotations.getNames(method);
            if (names != null && names.contains(cmdName)) {
                return method;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof CommandIdentifier))
            return false;
        CommandIdentifier commandIdentifier = (CommandIdentifier) o;
        return commandIdentifier.cls == this.cls && commandIdentifier.cmdName.equals(this.cmdName);
    }

    @Override
    public int hashCode() {
        int result = 55;
        return 7 * result + this.cls.hashCode() + this.cmdName.hashCode();
    }
}