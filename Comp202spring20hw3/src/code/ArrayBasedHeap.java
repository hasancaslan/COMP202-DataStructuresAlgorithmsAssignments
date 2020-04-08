package code;

import given.Entry;
import given.iAdaptablePriorityQueue;

import java.util.ArrayList;
import java.util.Comparator;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 *
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

    // Use this arraylist to store the nodes of the heap.
    // This is required for the autograder.
    // It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but then you do not have to deal with dynamic resizing
    protected ArrayList<Entry<Key, Value>> nodes;
    private Comparator<Key> comparator;
    private int lastIndex;

    public ArrayBasedHeap() {
        nodes = new ArrayList<>();
        lastIndex = 0;
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public Comparator<Key> getComparator() {
        return comparator;
    }

    @Override
    public void setComparator(Comparator<Key> C) {
        this.comparator = C;
    }

    @Override
    public void insert(Key k, Value v) {
        Entry<Key, Value> node = new Entry<>(k, v);
        nodes.add(node);
        upHeap(lastIndex);
        lastIndex++;
    }

    @Override
    public Entry<Key, Value> pop() {
        if (isEmpty()) return null;

        Entry<Key, Value> poppedNode = nodes.get(0);
        swap(0, lastIndex - 1);
        nodes.remove(lastIndex - 1);
        lastIndex--;
        downHeap(0);
        return poppedNode;
    }

    @Override
    public Entry<Key, Value> top() {
        if (isEmpty()) return null;
        return nodes.get(0);
    }

    @Override
    public Value remove(Key k) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getKey().equals(k)) {
                Value value = nodes.get(i).getValue();
                swap(i, lastIndex - 1);
                nodes.remove(lastIndex - 1);
                lastIndex--;

                if (i == lastIndex) return value;

                if (i == 0) {
                    downHeap(i);
                    return value;
                }

                int indexOfParent = parentOf(i);
                Key parent = nodes.get(indexOfParent).getKey();
                Key node = nodes.get(i).getKey();
                int result = comparator.compare(parent, node);
                if (result > 0) upHeap(i);
                else downHeap(i);

                return value;
            }
        }
        return null;
    }

    @Override
    public Key replaceKey(Entry<Key, Value> entry, Key k) {
        for (int i = 0; i < nodes.size(); i++) {
            Entry<Key, Value> node = nodes.get(i);
            Key key = node.getKey();
            Value value = node.getValue();
            if (key.equals(entry.getKey()) && value.equals(entry.getValue())) {
                node.setKey(k);

                if (i == 0) {
                    downHeap(i);
                    return entry.getKey();
                }

                int indexOfParent = parentOf(i);
                node = nodes.get(i);
                key = node.getKey();
                Key parent = nodes.get(indexOfParent).getKey();
                int result = comparator.compare(parent, key);
                if (result < 0) downHeap(i);
                else upHeap(i);
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Key replaceKey(Value v, Key k) {
        for (int i = 0; i < nodes.size(); i++) {
            Entry<Key, Value> node = nodes.get(i);
            Key key = node.getKey();
            Key oldKey = key;
            Value value = node.getValue();
            if (value.equals(v)) {
                node.setKey(k);
                if (i == 0) {
                    downHeap(i);
                    return oldKey;
                }

                int indexOfParent = parentOf(i);
                node = nodes.get(i);
                key = node.getKey();
                Key parent = nodes.get(indexOfParent).getKey();
                int result = comparator.compare(parent, key);
                if (result < 0) downHeap(i);
                else upHeap(i);

                return oldKey;
            }
        }
        return null;
    }

    @Override
    public Value replaceValue(Entry<Key, Value> entry, Value v) {
        for (int i = 0; i < nodes.size(); i++) {
            Entry<Key, Value> node = nodes.get(i);
            Key key = node.getKey();
            Value value = node.getValue();
            if (key.equals(entry.getKey()) && value.equals(entry.getValue())) {
                node.setValue(v);
                return value;
            }
        }
        return null;
    }

    private int parentOf(int i) {
        return (i - 1) / 2;
    }

    private int rightChildOf(int i) {
        return 2 * i + 2;
    }

    private int leftChildOf(int i) {
        return 2 * i + 1;
    }

    private boolean hasRightChild(int i) {
        return rightChildOf(i) < nodes.size();
    }

    private boolean hasLeftChild(int i) {
        return leftChildOf(i) < nodes.size();
    }

    private void swap(int i, int j) {
        Entry<Key, Value> e = nodes.get(i);
        nodes.set(i, nodes.get(j));
        nodes.set(j, e);
    }

    private void upHeap(int index) {
        while (index > 0) {
            int indexOfParent = parentOf(index);
            Key node = nodes.get(index).getKey();
            Key parent = nodes.get(indexOfParent).getKey();
            int result = comparator.compare(node, parent);
            if (result >= 0) break;

            swap(index, indexOfParent);
            index = indexOfParent;
        }
    }

    private void downHeap(int index) {
        while (hasLeftChild(index)) {
            int indexOfSmallChild = leftChildOf(index);
            int result;
            if (hasRightChild(index)) {
                int indexOfRightChild = rightChildOf(index);
                Key rightChild = nodes.get(indexOfRightChild).getKey();
                Key smallChild = nodes.get(indexOfSmallChild).getKey();
                result = comparator.compare(rightChild, smallChild);
                if (result < 0)
                    indexOfSmallChild = indexOfRightChild;
            }

            Key node = nodes.get(index).getKey();
            Key smallChild = nodes.get(indexOfSmallChild).getKey();
            result = comparator.compare(smallChild, node);
            if (result >= 0) break;

            swap(index, indexOfSmallChild);
            index = indexOfSmallChild;
        }
    }
}
