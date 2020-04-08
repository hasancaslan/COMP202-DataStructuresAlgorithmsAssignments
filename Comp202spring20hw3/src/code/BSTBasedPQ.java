package code;

import given.Entry;
import given.iAdaptablePriorityQueue;

import java.util.List;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {
    @Override
    public void insert(Key k, Value v) {
        put(k, v);
    }

    @Override
    public Entry<Key, Value> pop() {
        BinaryTreeNode<Key, Value> entry = getLastNode();
        if (isExternal(entry)) return null;

        Entry<Key, Value> oldEntry = new Entry<>(entry.getKey(), entry.getValue());
        remove(entry.getKey());
        return oldEntry;
    }

    @Override
    public Entry<Key, Value> top() {
        BinaryTreeNode<Key, Value> entry = getLastNode();
        if (isExternal(entry)) return null;
        return entry;
    }

    @Override
    public Key replaceKey(Entry<Key, Value> entry, Key k) {
        Key key = entry.getKey();
        Value value = entry.getValue();
        BinaryTreeNode<Key, Value> node = getNode(key);

        if (isExternal(node) || !entry.getValue().equals(node.getValue()))
            return null;

        remove(key);
        put(k, value);
        return key;
    }

    @Override
    public Key replaceKey(Value v, Key k) {
        List<BinaryTreeNode<Key, Value>> nodesInOrder = getNodesInOrder();
        for (BinaryTreeNode<Key, Value> node : nodesInOrder) {
            if (node.getValue().equals(v))
                return replaceKey(node, k);
        }
        return null;
    }

    @Override
    public Value replaceValue(Entry<Key, Value> entry, Value v) {
        Key key = entry.getKey();
        BinaryTreeNode<Key, Value> node = getNode(key);
        if (isExternal(node) || !entry.getValue().equals(node.getValue())) {
            return null;
        }
        return put(key, v);
    }
}
