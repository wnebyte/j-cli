package com.github.wnebyte.jcli.struct;

import java.util.Collection;
import java.util.HashSet;

public class ClassSet extends DerivedSet<Object> {

    public ClassSet() {
        super.set = new HashSet<>();
    }

    @Override
    public boolean add(Object o) {
        if (contains(o)) {
            return false;
        } else {
            set.add(o);
            return true;
        }
    }

    @Override
    public boolean contains(Object o) {
        return o != null && set.stream().anyMatch(o1 -> o1.getClass() == o.getClass());
    }

    @Override
    public boolean addAll(Collection<?> c) {
        if (c != null) {
            for (Object o : c) {
                add(o);
            }
        }
        return true;
    }

    public Object get(Class<?> aClass) {
        return set.stream().filter(o -> o.getClass() == aClass).findFirst().orElse(null);
    }

    public int size() {
        return set.size();
    }
}