package code;

import given.AbstractArraySort;

/*
 * Implement the heap-sort algorithm here. You can look at the slides for the pseudo-code.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 *
 */

public class HeapSort<K extends Comparable<K>> extends AbstractArraySort<K> {

    public HeapSort() {
        name = "Heapsort";
    }

    @Override
    public void sort(K[] inputArray) {
        heapify(inputArray);
        int lastIndex = inputArray.length - 1;
        while (0 < lastIndex) {
            swap(inputArray, 0, lastIndex--);
            downheap(inputArray, 0, lastIndex);
        }
    }

    // Public since we are going to check its output!
    public void heapify(K[] inputArray) {
        int i = inputArray.length / 2 - 1;
        int lastIndex = inputArray.length - 1;
        for (; i >= 0; i--) {
            downheap(inputArray, i, lastIndex);
        }
    }

    // The below methods are given given as suggestion. You do not need to use them.
    // Feel free to add more methods

    protected void downheap(K[] inputArray, int i, int last) {
        int index = i;
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        if (leftChild <= last && compare(inputArray[index], inputArray[leftChild]) < 0) {
            index = leftChild;
        }
        if (rightChild <= last && compare(inputArray[index], inputArray[rightChild]) < 0) {
            index = rightChild;
        }
        if (index != i) {
            swap(inputArray, i, index);
            downheap(inputArray, index, last);
        }
    }
}
