package com.github.wnebyte.jshell.struct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
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
    public Set<K> keySet() {
        return super.keySet();
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return super.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
