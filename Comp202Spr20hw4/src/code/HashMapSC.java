package code;

import given.AbstractHashMap;
import given.HashEntry;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Set;

/*
 * The file should contain the implementation of a hashmap with:
 * - Separate Chaining for collision handling
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Java's own linked lists for the secondary containers
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 *   Note that for this type of hashmap, load factor can be higher than 1
 *
 * Some helper functions are provided to you. We suggest that you go over them.
 *
 * You are not allowed to use any existing java data structures other than for the buckets (which have been
 * created for you) and the keyset method
 */

public class HashMapSC<Key, Value> extends AbstractHashMap<Key, Value> {

    // The threshold of the load factor for resizing
    protected float criticalLoadFactor;
    // The underlying array to hold hash entry Lists
    private LinkedList<HashEntry<Key, Value>>[] buckets;

    // Default constructor
    public HashMapSC() {
        this(101);
    }

    public HashMapSC(int initSize) {
        // High criticalAlpha for representing average items in a secondary container
        this(initSize, 10f);
    }

    public HashMapSC(int initSize, float criticalAlpha) {
        N = initSize;
        criticalLoadFactor = criticalAlpha;
        resizeBuckets(N);

        // Set up the MAD compression and secondary hash parameters
        updateHashParams();
    }

    // Note that the Linkedlists are still not initialized!
    @SuppressWarnings("unchecked")
    protected void resizeBuckets(int newSize) {
        // Update the capacity
        N = nextPrime(newSize);
        buckets = (LinkedList<HashEntry<Key, Value>>[]) Array.newInstance(LinkedList.class, N);
    }

    public int hashValue(Key key, int iter) {
        return hashValue(key);
    }

    public int hashValue(Key key) {
        int k = Math.abs(key.hashCode());
        return Math.abs((a * k + b) % P) % N;
    }

    @Override
    public Value get(Key k) {
        if (k == null)
            return null;

        int n = hashValue(k);
        if (buckets[n] == null)
            return null;

        for (HashEntry<Key, Value> entry : buckets[n]) {
            if (entry.getKey().equals(k))
                return entry.getValue();
        }

        return null;
    }

    @Override
    public Value put(Key k, Value v) {
        if (k == null)
            return null;

        checkAndResize();
        int s = hashValue(k);
        if (buckets[s] == null)
            buckets[s] = new LinkedList<>();

        for (HashEntry<Key, Value> entry : buckets[s]) {
            if (entry.getKey().equals(k)) {
                Value value = entry.getValue();
                entry.setValue(v);
                return value;
            }
        }

        buckets[s].add(new HashEntry<>(k, v));
        n++;
        return null;
    }

    @Override
    public Value remove(Key k) {
        if (k == null)
            return null;

        int s = hashValue(k);
        if (buckets[s] == null)
            return null;

        Value value = get(k);
        if (value == null)
            return null;

        buckets[s].remove(new HashEntry<>(k, value));
        n--;
        return value;
    }

    @Override
    public Iterable<Key> keySet() {
        Set<Key> keys = new java.util.HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                for (HashEntry<Key, Value> entry : buckets[i])
                    keys.add(entry.getKey());
            }
        }
        return keys;
    }

    /**
     * checkAndResize checks whether the current load factor is greater than the
     * specified critical load factor. If it is, the table size should be increased
     * to 2*N and recreate the hash table for the keys (rehashing). Do not forget to
     * re-calculate the hash parameters and do not forget to re-populate the new
     * array!
     */
    protected void checkAndResize() {
        if (loadFactor() > criticalLoadFactor) {
            LinkedList<HashEntry<Key, Value>>[] previousHashArray = buckets;
            resizeBuckets(2 * N);
            updateHashParams();
            for (int i = 0; i < previousHashArray.length; i++) {
                if (previousHashArray[i] != null) {
                    for (HashEntry<Key, Value> entry : previousHashArray[i]) {
                        int s = hashValue(entry.getKey());
                        if (buckets[s] == null)
                            buckets[s] = new LinkedList<>();
                        buckets[s].add(entry);
                    }
                }
            }
        }
    }
}
