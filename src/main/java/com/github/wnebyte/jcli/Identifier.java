package com.github.wnebyte.jcli;

import java.lang.reflect.Method;
import java.util.Set;
import com.github.wnebyte.jcli.util.Annotations;

public class Identifier {

    private final Class<?> cls;

    private final String cmdName;

    public Identifier(Class<?> cls, String cmdName) {
        this.cls = cls;
        this.cmdName = cmdName;
    }

    public Method getMethod() {
        for (Method method : cls.getDeclaredMethods()) {
            Set<String> names = Annotations.getNames(method);
            if (names.contains(cmdName)) {
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
        if (!(o instanceof Identifier))
            return false;
        Identifier identifier = (Identifier) o;
        return identifier.cls == this.cls && identifier.cmdName.equals(this.cmdName);
    }

    @Override
    public int hashCode() {
        int result = 55;
        return 7 * result + this.cls.hashCode() + this.cmdName.hashCode();
    }
}