package code;

import given.AbstractHashMap;
import given.HashEntry;

import java.lang.reflect.Array;
import java.util.Set;

/*
 * The file should contain the implementation of a hashmap with:
 * - Open addressing for collision handling
 * - Double hashing for probing. The double hash function should be of the form: q - (k mod q)
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 *
 * Some helper functions are provided to you. We suggest that you go over them.
 *
 * You are not allowed to use any existing java data structures other than for the keyset method
 */

public class HashMapDH<Key, Value> extends AbstractHashMap<Key, Value> {

    // The threshold of the load factor for resizing
    protected float criticalLoadFactor;
    // The prime number for the secondary hash
    int dhP;
    // The underlying array to hold hash entries (see the HashEntry class)
    private HashEntry<Key, Value>[] buckets;

    // Invalid entry to be used for removing entries.
    private HashEntry<Key, Value> invalidEntry = new HashEntry<>(null, null);

    // Default constructor
    public HashMapDH() {
        this(101);
    }

    public HashMapDH(int initSize) {
        this(initSize, 0.6f);
    }

    public HashMapDH(int initSize, float criticalAlpha) {
        N = initSize;
        criticalLoadFactor = criticalAlpha;
        resizeBuckets(N);

        // Set up the MAD compression and secondary hash parameters
        updateHashParams();
    }

    //Do not forget to call this when you need to increase the size!
    @SuppressWarnings("unchecked")
    protected void resizeBuckets(int newSize) {
        // Update the capacity
        N = nextPrime(newSize);
        buckets = (HashEntry<Key, Value>[]) Array.newInstance(HashEntry.class, N);
    }

    private HashEntry<Key, Value> getBucket(Key k) {
        for (int i = 0; i < N; i++) {
            int s = hashValue(k, i);
            if (buckets[s] == null)
                return null;

            if (isValid(buckets[s]) && buckets[s].getKey().equals(k)) {
                return buckets[s];
            }
        }

        return null;
    }

    // Puts entry to internal place. If place is occupied returns old entry.
    private Value putEntry(Key k, Value v) {
        HashEntry<Key, Value> bucket = getBucket(k);

        if (bucket != null) {
            Value value = bucket.getValue();
            bucket.setValue(v);
            return value;
        }

        for (int i = 0; i < N; i++) {
            int s = hashValue(k, i);
            if (buckets[s] == null || !isValid(buckets[s])) {
                buckets[s] = new HashEntry<>(k, v);
                n++;
                return null;
            }
        }

        return null;
    }

    // Validation Check
    private boolean isValid(HashEntry entry) {
        return entry != invalidEntry;
    }

    /**
     * Calculates the hash value by compressing the given hashcode. Note that you
     * need to use the Multiple-Add-Divide method. The class variables "a" is the
     * scale, "b" is the shift, "mainP" is the prime which are calculated for you.
     * Do not include the size of the array here
     * <p>
     * Make sure to include the absolute value since there maybe integer overflow!
     */
    protected int primaryHash(int hashCode) {
        return Math.abs((a * hashCode + b) % P);
    }

    /**
     * The secondary hash function. Remember you need to use "dhP" here!
     */
    protected int secondaryHash(int hashCode) {
        return (dhP - (hashCode % dhP));
    }

    @Override
    public int hashValue(Key key, int iter) {
        int k = Math.abs(key.hashCode());
        return Math.abs(primaryHash(k) + iter * secondaryHash(k)) % N;
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
            HashEntry<Key, Value>[] previousHashArray = buckets;
            resizeBuckets(2 * N);
            updateHashParams();
            for (int i = 0; i < previousHashArray.length; i++) {
                if (previousHashArray[i] != null && isValid(previousHashArray[i])) {
                    putEntry(previousHashArray[i].getKey(), previousHashArray[i].getValue());
                }
            }
        }
    }

    @Override
    public Value get(Key k) {
        if (k == null)
            return null;

        HashEntry<Key, Value> bucket = getBucket(k);
        return bucket == null ? null : bucket.getValue();
    }

    @Override
    public Value put(Key k, Value v) {
        if (k == null)
            return null;

        checkAndResize();
        return putEntry(k, v);
    }

    @Override
    public Value remove(Key k) {
        if (k == null)
            return null;

        for (int i = 0; i < N; i++) {
            int s = hashValue(k, i);
            if (buckets[s] == null)
                return null;

            if (isValid(buckets[s]) && buckets[s].getKey().equals(k)) {
                Value value = buckets[s].getValue();
                buckets[s] = invalidEntry;
                n--;
                return value;
            }
        }

        return null;
    }

    // This is the only function you are allowed to use an existing Java data
    // structure!
    @Override
    public Iterable<Key> keySet() {
        Set<Key> keys = new java.util.HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            if (isValid(buckets[i]) && buckets[i] != null)
                keys.add(buckets[i].getKey());
        }

        return keys;
    }

    @Override
    protected void updateHashParams() {
        super.updateHashParams();
        dhP = nextPrime(N / 2);
    }
}
