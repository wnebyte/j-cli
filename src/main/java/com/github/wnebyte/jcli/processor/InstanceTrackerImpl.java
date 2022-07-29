package com.github.wnebyte.jcli.processor;

import java.util.Collection;
import com.github.wnebyte.jcli.util.TypeSet;
import com.github.wnebyte.jcli.di.IDependencyContainer;

/**
 * This class implements methods for keeping track of and instantiating Objects that are of distinct Classes.
 */
public class InstanceTrackerImpl implements InstanceTracker {

    /*
    ###########################
    #          FIELDS         #
    ###########################
    */

    /**
     * Is used to store Objects of distinct Classes.
     */
    private final TypeSet typeSet;

    private final IDependencyContainer dependencyContainer;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public InstanceTrackerImpl(IDependencyContainer dependencyContainer) {
        this.typeSet = new TypeSet();
        this.dependencyContainer = dependencyContainer;
    }

    /*
    ###########################
    #         METHODS         #
    ###########################
    */

    @Override
    public boolean add(Object o) {
        return typeSet.add(o);
    }

    @Override
    public boolean addAll(Collection<Object> c) {
        return typeSet.addAll(c);
    }

    @Override
    public int size() {
        return typeSet.size();
    }

    /**
     * Returns the <code>Object</code> that is of the specified <code>aClass</code> if one is being tracked, otherwise
     * constructs a new instance of <code>aClass</code> using this instance's {@link IDependencyContainer},
     * and tracks it.
     * @param cls the class of the Object.
     * @throws ReflectiveOperationException if no Object was tracked, and a new instance could not be constructed.
     */
    @Override
    public Object get(Class<?> cls) throws ReflectiveOperationException {
        Object object = typeSet.get(cls);
        if (object == null) {
            object = newInstance(cls);
            if (object != null) {
                typeSet.add(object);
            }
        }
        return object;
    }

    /**
     * Constructs and returns a new instance of the specified <code>aClass</code> using this instance's
     * {@link IDependencyContainer} (does not track/store the result).
     * @param cls the class of the Object to be instantiated.
     * @throws ReflectiveOperationException if a new instance could not be constructed.
     */
    @Override
    public Object newInstance(Class<?> cls) throws ReflectiveOperationException {
        return dependencyContainer.newInstance(cls);
    }

    @Override
    public boolean canInstantiate(Class<?> cls) {
        return dependencyContainer.canInstantiate(cls);
    }
}