package util;

import core.IConsole;
import exception.config.NoDefaultConstructorException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class InstanceTracker {

    private final Set<Object> objects;

    private IConsole injectable;

    public InstanceTracker() {
        this.objects = new HashSet<>();
    }

    /**
     * Secondary Constructor.
     * @param objects to populate tracker with, objects belonging to duplicate classes will be removed.
     */
    public InstanceTracker(Set<Object> objects) {
        this.objects = Objects.requireNonNullElse(objects, new HashSet<>());
        this.objects.removeIf(object ->
                getNotThis(object).stream().anyMatch(obj -> obj.getClass() == object.getClass()));
    }

    public final void setInjectable(IConsole console) {
        this.injectable = console;
    }

    public final Object addClass(Class<?> objectClass) throws NoDefaultConstructorException {
        return contains(objectClass) ?
                get(objectClass) :
                newInstance(objectClass);
    }

    /**
     * Adds the specified <code>Object</code> to the tracker, if it is NON-NULL, and the tracker is not already
     * tracking an <code>Object</code> of the same class.
     * <p/>
     * @param object the <code>Object</code> to be tracked.
     * @return true if successful, otherwise false.
     */
    public final boolean addObject(Object object) {
        if ((object != null) && (!(contains(object.getClass())))) {
            objects.add(object);
            return true;
        }
        return false;
    }

    public boolean contains(Class<?> objectClass) {
        return objects.stream().anyMatch(object -> object.getClass() == objectClass);
    }

    public final int size() {
        return objects.size();
    }

    private Object newInstance(final Class<?> cls) throws NoDefaultConstructorException {
        Object newInstance = null;
        Constructor<?> constructor;
        boolean success = false;

        if (injectable != null) {
            try {
                constructor = Arrays.stream(cls.getConstructors())
                        .filter(con -> con.getParameterCount() == 1 &&
                                IConsole.class.isAssignableFrom(con.getParameterTypes()[0]))
                        .findFirst().orElseThrow();
                constructor.setAccessible(true);
                newInstance = constructor.newInstance(injectable);
                success = true;
            } catch (Exception ignored) {}
        }
        if (!success) {
            try {
                constructor = cls.getConstructor();
                constructor.setAccessible(true);
                newInstance = constructor.newInstance();
            } catch (Exception e) {
                throw new NoDefaultConstructorException(
                        "No default constructor is available for: " + cls
                );
            }
        }
        objects.add(newInstance);
        return newInstance;
    }

    private Set<Object> getNotThis(Object object) {
        return objects.stream().filter(obj -> !(obj == object)).collect(Collectors.toSet());
    }

    private Object get(Class<?> objectClass) throws IllegalStateException {
        return objects.stream().filter(object -> object.getClass() == objectClass).findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "class: "  + objectClass + " is not present within set."
                ));
    }
}
