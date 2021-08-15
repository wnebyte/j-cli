package com.github.wnebyte.jshell.struct;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class BiImmutableMap<K, V> extends HashMap<K, V> {

    public BiImmutableMap() {
        super();
    }

    public BiImmutableMap(Map<? extends K, ? extends V> map) {
        super(map);
    }

    public BiImmutableMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return super.get(key);
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException(
                "put operation is not supported"
        );
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return super.putIfAbsent(key, value);
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException(
                "remove operation is not supported"
        );
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException(
                "removeAll operation is not supported"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
                "clear operation is not supported"
        );
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

}
