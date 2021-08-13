package com.github.wnebyte.jshell.util;

import com.github.wnebyte.jshell.IConsole;
import com.github.wnebyte.jshell.exception.config.NoReqConstructorException;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This class provides methods and internals for tracking and instantiating Objects of distinct classes.
 */
public final class InstanceTracker {

    private final Set<Object> objects;

    private IConsole injectable;

    /**
     * Constructs a new <code>InstanceTracker</code> instance.
     */
    public InstanceTracker() {
        this.objects = new HashSet<>();
    }

    /**
     * Constructs a new <code>InstanceTracker</code> instance using the Objects that are of a distinct class present
     * within the specified <code>Set</code>.
     * @param objects the Objects to initialize this <code>InstanceTracker</code> with.
     */
    public InstanceTracker(final Set<Object> objects) {
        this.objects = ObjectUtil.requireNonNullElseGet(distinct(objects), HashSet::new);
    }

    /**
     * Configure a specified <code>IConsole</code> to be injected into any available declared constructors of
     * of the distinct classes of this class's tracked Objects.
     * @param console the <code>IConsole</code> to be injected, if possible.
     */
    public final void setInjectable(final IConsole console) {
        this.injectable = console;
    }

    /**
     * Tracks the specified Object if it is non <code>null</code>,
     * and if the tracker is not already tracking an Object of the same class.
     * @param object the Object to be tracked.
     * @return true if there were no Objects of the same class already being tracked
     * and the Object was successfully tracked, otherwise false.
     */
    public final boolean trackObject(final Object object) {
        if ((object != null) && (!(contains(object.getClass())))) {
            objects.add(object);
            return true;
        }
        return false;
    }

    /**
     * Returns the Object that is of the specified class if one is tracked, otherwise constructs
     * and returns a new instance of the specified class.
     * @param cls the class of the Object.
     * @return the tracked Object of the specified class if one exists, or a new instance of the specified class.
     * @throws NoReqConstructorException if neither of the required constructors are declared in the specified class
     * in the event of there being no tracked Object of the specified class.
     */
    public final Object getObject(final Class<?> cls) throws NoReqConstructorException {
        return contains(cls) ? get(cls) : newInstance(cls);
    }

    /**
     * Determines whether an Object of the specified class is tracked.
     * @param cls the class of the Object.
     * @return true if an Object of the specified class is tracked, otherwise false.
     */
    public boolean contains(final Class<?> cls) {
        return objects.stream().anyMatch(object -> object.getClass() == cls);
    }

    /**
     * @return the number of tracked Objects.
     */
    public final int size() {
        return objects.size();
    }

    /**
     * Constructs a new instance of the specified class.
     * @param cls the class.
     * @return the new instance.
     * @throws NoReqConstructorException if neither of the required constructors are declared in the specified
     * class.
     */
    private Object newInstance(final Class<?> cls) throws NoReqConstructorException {
        Object newInstance = null;
        Constructor<?> constructor;
        boolean success = false;

        if (injectable != null) {
            try {
                constructor = Arrays.stream(cls.getConstructors())
                        .filter(con -> (con.getParameterCount() == 1) && (IConsole.class
                                .isAssignableFrom(con.getParameterTypes()[0])))
                        .findFirst()
                        .orElseThrow((Supplier<Throwable>) Exception::new);
                constructor.setAccessible(true);
                newInstance = constructor.newInstance(injectable);
                success = true;
            } catch (Throwable ignored) {
                // ignore
            }
        }
        if (!success) {
            try {
                constructor = cls.getConstructor();
                constructor.setAccessible(true);
                newInstance = constructor.newInstance();
            } catch (Exception e) {
                throw new NoReqConstructorException(
                        "Neither of the required constructors are declared in class: " + cls
                );
            }
        }
        objects.add(newInstance);
        return newInstance;
    }

    /**
     * Removes any Objects that are of a duplicate class from the specified <code>Set</code>.
     * @param objects the <code>Set</code>.
     * @return the <code>Set</code>.
     */
    private Set<Object> distinct(final Set<Object> objects) {
        Set<Class<?>> classes = new HashSet<>();
        if (objects == null) {
            return null;
        }

        for (Object object : new HashSet<>(objects)) {
            Class<?> cls = object.getClass();

            if (classes.contains(cls)) {
                objects.remove(object);
            } else {
                classes.add(cls);
            }
        }

        return objects;
    }

    /**
     * Returns the tracked Object of the specified class, if one exists.
     * @param cls the class.
     * @return the Object.
     * @throws IllegalStateException if no Object of the specified class is tracked.
     */
    private Object get(final Class<?> cls) throws IllegalStateException {
        return objects.stream().filter(object -> object.getClass() == cls).findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "class: "  + cls + " is not present within tracked set"
                ));
    }
}
