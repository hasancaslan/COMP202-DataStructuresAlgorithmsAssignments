package code;

import given.AbstractArraySort;

public class InsertionSort<K extends Comparable<K>> extends AbstractArraySort<K> {

    public InsertionSort() {
        name = "Insertionsort";
    }

    @Override
    public void sort(K[] inputArray) {
        int n = inputArray.length;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j > 0 && compare(inputArray[j - 1], inputArray[j]) > 0) {
                swap(inputArray, j - 1, j);
                j--;
            }
        }
    }

}
