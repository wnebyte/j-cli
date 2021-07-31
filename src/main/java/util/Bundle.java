package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Bundle {

    private final Object owner;

    private final Set<String> methodNames;

    public Bundle(Object owner, String... methodNames) {
        if (owner == null) {
            throw new IllegalArgumentException(
                    "Object must be non null."
            );
        }
        this.owner = owner;
        if (methodNames != null) {
            this.methodNames = new HashSet<>(Arrays.asList(methodNames));
        } else {
            this.methodNames = new HashSet<>();
        }
    }

    public Object getOwner() {
        return owner;
    }

    public Set<String> getMethodNames() {
        return methodNames;
    }
}
