package io.github.thelordman.costrength.quests.utility;

public record KeyValue<K, V>(K key, V value) {

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

}
