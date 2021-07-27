package util;

import exception.config.NoDefaultConstructorException;
import core.IConsole;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class InstanceTracker {

    private final Set<Object> objects;

    private IConsole injectable;

    public InstanceTracker() {
        this.objects = new HashSet<>();
    }

    public InstanceTracker(Set<Object> objects) {
        this.objects = Objects.requireNonNullElse(objects, new HashSet<>());
    }

    public final void setInjectable(IConsole console) {
        this.injectable = console;
    }

    public final Object add(Class<?> objectClass) throws NoDefaultConstructorException {
        return exists(objectClass) ?
                get(objectClass) :
                newInstance(objectClass);
    }

    public final void add(Object object) {
        assert !(exists(object.getClass()));
        objects.add(object);
    }

    private Object newInstance(Class<?> objectClass) throws NoDefaultConstructorException {
        Object newInstance = null;
        Constructor<?> constructor;
        boolean success = false;

        if (injectable != null) {
            try {
                constructor = objectClass.getConstructor(IConsole.class);
                constructor.setAccessible(true);
                newInstance = constructor.newInstance(injectable);
                success = true;
            } catch (Exception ignored) {}
        }

        if (!success) {
            try {
                constructor = objectClass.getConstructor();
                constructor.setAccessible(true);
                newInstance = constructor.newInstance();
            } catch (Exception e) {
                throw new NoDefaultConstructorException(
                        "No default constructor is available for: " + objectClass
                );
            }
        }

        return newInstance;
    }

    private boolean exists(Class<?> objectClass) {
        return objects.stream().anyMatch(o -> o.getClass() == objectClass);
    }

    private Object get(Class<?> objectClass) throws IllegalStateException {
        return objects.stream().filter(o -> o.getClass() == objectClass).findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "class: "  + objectClass + " is not present within set."
                ));
    }
}
