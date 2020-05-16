package code;

import given.AbstractArraySort;

/*
 * Implement the quick-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 *
 */

public class QuickSort<K extends Comparable<K>> extends AbstractArraySort<K> {

    public QuickSort() {
        name = "Quicksort";
    }

    @Override
    public void sort(K[] inputArray) {
        recursiveQuickSort(inputArray, 0, inputArray.length - 1);
    }

    // Public since we are going to check its output!
    public indexPair partition(K[] inputArray, int lo, int hi, int p) {
        int i = lo;
        int j = lo;
        int k = hi + 1;
        K pivot = inputArray[p];

        while (j < k) {
            if (compare(inputArray[j], pivot) < 0) {
                swap(inputArray, j++, i++);
            } else if (compare(inputArray[j], pivot) == 0) {
                j++;
            } else {
                swap(inputArray, j, --k);
            }
        }

        return new indexPair(i, k);
    }

    //The below methods are given given as suggestion. You do not need to use them.
    // Feel free to add more methods
    protected int pickPivot(K[] inputArray, int lo, int hi) {
        int mid = (lo + hi) / 2;
        if (compare(inputArray[lo], inputArray[mid]) > 0) {
            if (compare(inputArray[mid], inputArray[hi]) > 0)
                return mid;
            else if (compare(inputArray[lo], inputArray[hi]) > 0)
                return hi;
            else
                return lo;

        } else {
            if (compare(inputArray[lo], inputArray[hi]) > 0)
                return lo;
            else if (compare(inputArray[mid], inputArray[hi]) > 0)
                return hi;
            else
                return mid;
        }
    }

    private void recursiveQuickSort(K[] inputArray, int lo, int hi) {
        if (lo < hi) {
            int p = pickPivot(inputArray, lo, hi);
            indexPair pair = partition(inputArray, lo, hi, p);
            recursiveQuickSort(inputArray, lo, pair.p1);
            recursiveQuickSort(inputArray, pair.p2, hi);
        }
    }
  
  /* Alternative, if you are just returning an integer
  public int partition(K[] inputArray, int lo, int hi, int p)
  {
    //TODO:: Implement a partitioning function here
    return null;
  }*/

    //useful if we want to return a pair of indices from the partition function.
    //You do not need to use this if you are just returning and integer from the partition
    public class indexPair {
        public int p1, p2;

        indexPair(int pos1, int pos2) {
            p1 = pos1;
            p2 = pos2;
        }

        public String toString() {
            return "(" + Integer.toString(p1) + ", " + Integer.toString(p2) + ")";
        }
    }
}
