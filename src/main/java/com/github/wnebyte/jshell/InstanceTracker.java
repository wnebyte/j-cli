package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.exception.config.NoReqConstructorException;
import com.github.wnebyte.jshell.util.ObjectUtil;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Supplier;

/**
 * This class declares methods for keeping track of and instantiating Objects belonging to
 * distinct Classes.
 */
public final class InstanceTracker {

    private final Set<Object> objects;

    private IConsole injectable;

    /**
     * Constructs a new instance.
     */
    public InstanceTracker() {
        this.objects = new HashSet<>();
    }

    /**
     * Constructs a new instance using the specified Objects that are of a distinct class.
     * @param objects to initialize the new instance with.
     */
    public InstanceTracker(final Set<Object> objects) {
        this.objects = ObjectUtil.requireNonNullElseGet(distinct(objects), HashSet::new);
    }

    /**
     * Specify that the specified IConsole should be injected into any appropriate Constructors when
     * instantiating new Objects.
     * @param console the IConsole.
     */
    public final void setInjectable(final IConsole console) {
        this.injectable = console;
    }

    /**
     * Adds the specified Object for tracking if it is non <code>null</code> and if this Class
     * is not already tracking an Object of the same Class.
     * @param object to be tracked.
     * @return <code>true</code> if the Object was added successfully and there was no previous
     * Object of the same Class being tracked,
     * otherwise <code>false</code>.
     */
    public final boolean trackObject(final Object object) {
        if ((object != null) && (!(contains(object.getClass())))) {
            objects.add(object);
            return true;
        }
        return false;
    }

    /**
     * Returns the Object that is of the specified Class if one is currently being tracked, otherwise constructs
     * and returns a new instance of the specified Class.
     * @param cls the class of the Object.
     * @return the tracked Object if one exists, otherwise a new instance of the specified Class.
     * @throws NoReqConstructorException if no Object of the specified Class was being tracked and
     * no appropriate Constructor was found.
     */
    public final Object getObject(final Class<?> cls) throws NoReqConstructorException {
        return contains(cls) ? get(cls) : newInstance(cls);
    }

    /**
     * Returns whether an Object of the specified Class is currently being tracked.
     * @param cls the Class of the Object.
     * @return <code>true</code> if an Object of the specified Class is being tracked,
     * otherwise <code>false</code>.
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
     * Constructs a new instance of the specified Class.
     * @param cls the Class.
     * @return a new instance.
     * @throws NoReqConstructorException if neither of the required constructors are declared in the specified
     * Class.
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
     * Removes any Objects that are of a non distinct Class from the specified Set.
     * @param objects the Set of Objects.
     * @return a new Set.
     */
    private Set<Object> distinct(final Set<Object> objects) {
        Set<Class<?>> classes = new HashSet<>();
        if (objects == null) { return null; }

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
     * Returns the tracked Object that is of the specified Class if one exists.
     * @param cls the Class.
     * @return the Object if it exists, otherwise throws.
     * @throws IllegalStateException if no Object of the specified class is currently being tracked.
     */
    private Object get(final Class<?> cls) throws IllegalStateException {
        return objects.stream().filter(object -> object.getClass() == cls).findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "class: "  + cls + " is not present within tracked set"
                ));
    }
}