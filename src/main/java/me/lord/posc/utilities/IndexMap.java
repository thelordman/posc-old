package me.lord.posc.utilities;

import java.util.Collection;
import java.util.HashMap;

public class IndexMap<V> {
    private final HashMap<Integer, V> map = new HashMap<>();

    public int highestIndex() {
        return map.keySet().stream().mapToInt(t -> t).max().orElse(0);
    }

    public int nextIndex() {
        return highestIndex() + 1;
    }

    public V get(int index) {
        return map.get(index);
    }

    public V getOrDefault(int index, V defaultValue) {
        return map.getOrDefault(index, defaultValue);
    }

    public V put(V value) {
        return map.put(nextIndex(), value);
    }

    public V putIfAbsent(V value) {
        return map.putIfAbsent(nextIndex(), value);
    }

    public V remove(int index) {
        return map.remove(index);
    }

    public Collection<V> values() {
        return map.values();
    }
}
