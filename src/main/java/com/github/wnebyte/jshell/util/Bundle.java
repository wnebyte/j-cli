package com.github.wnebyte.jshell.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a test related class for "bundling" an Object and a Set of Methods declared in the
 * Class of said Object.
 */
public final class Bundle {

    private final Object owner;

    private final Set<String> methodNames;

    /**
     * Constructs a new instance using the specified Object and String varargs.
     * @param owner the instance.
     * @param methodNames the names of the Methods declared in the Class of the specified Object
     * to associate with the specified Object.
     */
    public Bundle(final Object owner, final String... methodNames) {
        if (owner == null) {
            throw new IllegalArgumentException(
                    "Object must be non null."
            );
        }
        this.owner = owner;
        this.methodNames = (methodNames != null) ?
                new HashSet<>(Arrays.asList(methodNames)) :
                new HashSet<>();
    }

    /**
     * @return this Bundle's Object.
     */
    public final Object getOwner() {
        return owner;
    }

    /**
     * @return this Bundle's Set of Methods.
     */
    public final Set<String> getMethodNames() {
        return methodNames;
    }
}
