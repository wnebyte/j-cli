package com.github.wnebyte.jcli.processor;

import java.util.Collection;
import com.github.wnebyte.jcli.struct.ClassSet;
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
    private final ClassSet classSet;

    private final IDependencyContainer dependencyContainer;

    /*
    ###########################
    #       CONSTRUCTORS      #
    ###########################
    */

    public InstanceTrackerImpl(IDependencyContainer dependencyContainer) {
        this.classSet = new ClassSet();
        this.dependencyContainer = dependencyContainer;
    }

    /*
    ###########################
    #         METHODS         #
    ###########################
    */

    @Override
    public boolean add(Object o) {
        return classSet.add(o);
    }

    @Override
    public boolean addAll(Collection<Object> c) {
        return classSet.addAll(c);
    }

    @Override
    public int size() {
        return classSet.size();
    }

    /**
     * Returns the <code>Object</code> that is of the specified <code>aClass</code> if one is being tracked, otherwise
     * constructs a new instance of <code>aClass</code> using this instance's {@link IDependencyContainer},
     * and tracks it.
     * @param aClass the class of the Object.
     * @throws ReflectiveOperationException if no Object was tracked, and a new instance could not be constructed.
     */
    @Override
    public Object get(Class<?> aClass) throws ReflectiveOperationException {
        Object object = classSet.get(aClass);
        if (object == null) {
            object = newInstance(aClass);
            if (object != null) {
                classSet.add(object);
            }
        }
        return object;
    }

    /**
     * Constructs and returns a new instance of the specified <code>aClass</code> using this instance's
     * {@link IDependencyContainer} (does not track/store the result).
     * @param aClass the class of the Object to be instantiated.
     * @throws ReflectiveOperationException if a new instance could not be constructed.
     */
    @Override
    public Object newInstance(Class<?> aClass) throws ReflectiveOperationException {
        Object newInstance = dependencyContainer.newInstance(aClass);
        return newInstance;
    }
}