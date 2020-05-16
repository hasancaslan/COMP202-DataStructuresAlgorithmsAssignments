package code;

import given.AbstractArraySort;

/*
 * Implement the c algorithm here. You can look at the slides for the pseudo-codes.
 * You do not have to use swap or compare from the AbstractArraySort here
 *
 * You may need to cast any K to Integer
 *
 */

public class CountingSort<K extends Comparable<K>> extends AbstractArraySort<K> {

    @SuppressWarnings("unchecked")

    public CountingSort() {
        name = "Countingsort";
    }

    @Override
    public void sort(K[] inputArray) {
        if (inputArray == null) {
            System.out.println("Null array");
            return;
        }
        if (inputArray.length < 1) {
            System.out.println("Empty array");
            return;
        }

        if (!(inputArray[0] instanceof Integer)) {
            System.out.println("Can only sort integers!");
            return;
        }

        K maxKey = inputArray[0];
        K minKey =  inputArray[0];

        int max = 0;
        int min = 0;

        for (int i = 1; i < inputArray.length; i++) {
            if (compare(maxKey, inputArray[i]) < 0) {
                maxKey = inputArray[i];
            } else if (compare(minKey, inputArray[i]) > 0) {
                minKey =  inputArray[i];
            }
        }

        if (maxKey instanceof Integer && minKey instanceof Integer) {
            max = (Integer) maxKey;
            min = (Integer) minKey;
        }

        int k = max - min + 1;
        int[] counts = new int[k];

        K[] tmp = (K[]) new Comparable[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            tmp[i] = inputArray[i];
        }

        for (int i = 0; i < inputArray.length; i++) {
            counts[(Integer) inputArray[i] - min]++;
        }

        for (int i = 1; i < k; i++) {
            counts[i] = counts[i] + counts[i - 1];
        }

        for (int i = inputArray.length - 1; i >= 0; i--) {
            int index = (Integer) tmp[i] - min;
            inputArray[counts[index] - 1] = tmp[i];
            counts[index]--;
        }
    }
}
