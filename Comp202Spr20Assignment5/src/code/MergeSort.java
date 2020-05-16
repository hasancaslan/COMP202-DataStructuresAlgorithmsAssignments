package code;

import given.AbstractArraySort;

/*
 * Implement the merge-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 *
 * You may need to create an Array of K (Hint: the auxiliary array).
 * Look at the previous assignments on how we did this!
 *
 */

public class MergeSort<K extends Comparable<K>> extends AbstractArraySort<K> {

    @SuppressWarnings("unchecked")

    K[] tmpArray;

    public MergeSort() {
        name = "Mergesort";
    }

    @Override
    public void sort(K[] inputArray) {
        tmpArray = (K[]) new Comparable[inputArray.length];
        int lo = 0;
        int hi = inputArray.length - 1;
        int mid = (lo + hi) / 2;
        recursiveMergeSort(inputArray, lo, mid, hi);
    }

    // Public since we are going to check its output!
    public void merge(K[] inputArray, int lo, int mid, int hi) {
        System.arraycopy(inputArray, lo, tmpArray, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        int k = lo;

        while (k <= hi) {
            if (j > hi || (i <= mid && compare(tmpArray[i], tmpArray[j]) <= 0)) {
                inputArray[k++] = tmpArray[i++];
            } else {
                inputArray[k++] = tmpArray[j++];
            }
        }
    }

    private void recursiveMergeSort(K[] inputArray, int lo, int mid, int hi) {
        if (lo < hi) {
            recursiveMergeSort(inputArray, lo, (lo + mid) / 2, mid);
            recursiveMergeSort(inputArray, mid + 1, (mid + hi + 1) / 2, hi);
            merge(inputArray, lo, mid, hi);
        }
    }
}
