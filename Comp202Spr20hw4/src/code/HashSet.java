package code;

import given.AbstractHashMap;
import given.iPrintable;
import given.iSet;

/*
 * A set class implemented with hashing. Note that there is no "value" here
 *
 * You are free to implement this however you want. Two potential ideas:
 *
 * - Use a hashmap you have implemented with a dummy value class that does not take too much space
 * OR
 * - Re-implement the methods but tailor/optimize them for set operations
 *
 * You are not allowed to use any existing java data structures
 *
 */

public class HashSet<Key> implements iSet<Key>, iPrintable<Key> {

    AbstractHashMap<Key, Object> hashMap;

    // A default public constructor is mandatory!
    public HashSet() {
        hashMap = new HashMapDH	<>();
    }

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return hashMap.size() == 0;
    }

    @Override
    public boolean contains(Key k) {
        return hashMap.get(k) != null;
    }

    @Override
    public boolean put(Key k) {
        return hashMap.put(k, 1) != null;
    }

    @Override
    public boolean remove(Key k) {
        return hashMap.remove(k) != null;
    }

    @Override
    public Iterable<Key> keySet() {
        return hashMap.keySet();
    }

    @Override
    public Object get(Key key) {
        // Do not touch
        return null;
    }
}
