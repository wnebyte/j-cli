package com.github.wnebyte.jcli;

import java.util.Collection;
import com.github.wnebyte.jcli.struct.ClassSet;
import com.github.wnebyte.jcli.di.IDependencyContainer;

public class InstanceTracker {

    private final ClassSet set;

    private final IDependencyContainer dependencyContainer;

    public InstanceTracker(IDependencyContainer dependencyContainer) {
        this.set = new ClassSet();
        this.dependencyContainer = dependencyContainer;
    }

    public boolean add(Object o) {
        return set.add(o);
    }

    public boolean addAll(Collection<Object> c) {
        return set.addAll(c);
    }

    public int size() {
        return set.size();
    }

    /**
     * Returns the <code>Object</code> that is of the specified <code>class</code> if one is being tracked, otherwise
     * constructs a new instance using this instance's {@link IDependencyContainer}, and tracks it.
     */
    public Object get(Class<?> aClass) {
        Object object = set.get(aClass);
        if (object == null) {
            object = newInstance(aClass);
            if (object != null) {
                set.add(object);
            }
        }
        return object;
    }

    /**
     * Constructs and returns a new instance of the specified <code>class</code> using this instance's
     * {@link IDependencyContainer} (does not track the result).
     */
    public Object newInstance(Class<?> aClass) {
        Object newInstance = dependencyContainer.newInstance(aClass);
        return newInstance;
    }
}