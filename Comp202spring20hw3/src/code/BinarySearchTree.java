package code;

import given.iBinarySearchTree;
import given.iMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {
    private Comparator<Key> comparator;
    private BinaryTreeNode<Key, Value> root;
    private int size;

    public BinarySearchTree() {
        root = new BinaryTreeNode<>(null, null);
        size = 0;
    }

    @Override
    public Value get(Key k) {
        if (k == null)
            return null;
        return treeSearch(k, root).getValue();
    }

    @Override
    public Value put(Key k, Value v) {
        if (k == null)
            return null;

        BinaryTreeNode<Key, Value> node = treeSearch(k, root);
        Value oldValue = node.getValue();
        node.setKey(k);
        node.setValue(v);

        if (isExternal(node)) {
            node.setLeft(new BinaryTreeNode<>(null, null, node, null, null));
            node.setRight(new BinaryTreeNode<>(null, null, node, null, null));
            size++;
        }

        return oldValue;
    }

    @Override
    public Value remove(Key k) {
        if (k == null) {
            return null;
        }
        BinaryTreeNode<Key, Value> node = treeSearch(k, root);
        if (isExternal(node)) {
            return null;
        }

        size--;
        return removeNode(node);
    }

    private Value removeNode(BinaryTreeNode<Key, Value> nodeToDelete) {
        Value oldValue = nodeToDelete.getValue();
        BinaryTreeNode<Key, Value> parent = nodeToDelete.getParent();

        if (numOfChildren(nodeToDelete) == 0) {
            if (!isRoot(nodeToDelete)) {
                if (isRightChild(nodeToDelete))
                    parent.setRight(createEmptyNode(parent));
                else
                    parent.setLeft(createEmptyNode(parent));
            } else {
                collectGarbage(root);
            }
        } else if (numOfChildren(nodeToDelete) == 1) {
            BinaryTreeNode<Key, Value> nodeToReplace;

            if (isExternal(nodeToDelete.getLeft()))
                nodeToReplace = nodeToDelete.getRight();
            else
                nodeToReplace = nodeToDelete.getLeft();

            nodeToReplace.setParent(parent);

            if (isRoot(nodeToDelete))
                root = nodeToReplace;
            else if (isRightChild(nodeToDelete))
                parent.setRight(nodeToReplace);
            else
                parent.setLeft(nodeToReplace);

            collectGarbage(nodeToDelete);
        } else {
            BinaryTreeNode<Key, Value> lastNode = lastNodeOfSubtree(nodeToDelete.getRight());
            nodeToDelete.setKey(lastNode.getKey());
            nodeToDelete.setValue(lastNode.getValue());
            removeNode(lastNode);
        }
        return oldValue;
    }

    @Override
    public Iterable<Key> keySet() {
        List<Key> keys = new ArrayList<>();
        for (BinaryTreeNode<Key, Value> node : getNodesInOrder()) {
            if (node.getKey() != null)
                keys.add(node.getKey());
        }
        return keys;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public BinaryTreeNode<Key, Value> getRoot() {
        return root;
    }

    @Override
    public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
        if (node != null)
            return node.getParent();
        return null;
    }

    @Override
    public boolean isInternal(BinaryTreeNode<Key, Value> node) {
        return node.getLeft() != null || node.getRight() != null;
    }

    @Override
    public boolean isExternal(BinaryTreeNode<Key, Value> node) {
        return node.getLeft() == null && node.getRight() == null;
    }

    @Override
    public boolean isRoot(BinaryTreeNode<Key, Value> node) {
        return node == root;
    }

    @Override
    public BinaryTreeNode<Key, Value> getNode(Key k) {
        return treeSearch(k, root);
    }

    @Override
    public Value getValue(Key k) {
        return get(k);
    }

    @Override
    public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
        if (node == null)
            return null;
        else
            return node.getLeft();
    }

    @Override
    public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
        if (node == null)
            return null;
        else
            return node.getRight();
    }

    @Override
    public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
        BinaryTreeNode<Key, Value> parent = getParent(node);
        if (parent == null)
            return null;
        if (node == parent.getLeft())
            return parent.getRight();
        else
            return parent.getLeft();
    }

    @Override
    public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
        return node != null && !isRoot(node) && node.getParent().getLeft() == node;
    }

    @Override
    public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
        return node != null && !isRoot(node) && node.getParent().getRight() == node;
    }

    @Override
    public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
        List<BinaryTreeNode<Key, Value>> nodesInOrder = new ArrayList<>();
        if (!isEmpty())
            inOrderSubtree(root, nodesInOrder);
        return nodesInOrder;
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
    public BinaryTreeNode<Key, Value> ceiling(Key k) {
        BinaryTreeNode<Key, Value> node = treeSearch(k, root);
        if (!isExternal(node))
            return node;
        while (isRightChild(node))
            node = node.getParent();
        if (isLeftChild(node))
            return node.getParent();
        else
            return null;
    }

    @Override
    public BinaryTreeNode<Key, Value> floor(Key k) {
        BinaryTreeNode<Key, Value> node = treeSearch(k, root);
        if (!isExternal(node))
            return node;
        while (isLeftChild(node))
            node = node.getParent();
        if (isRightChild(node))
            return node.getParent();
        else
            return null;
    }

    private BinaryTreeNode<Key, Value> lastNodeOfSubtree(BinaryTreeNode<Key, Value> node) {
        if (isExternal(node) || isExternal(node.getLeft()))
            return node;
        else
            return lastNodeOfSubtree(node.getLeft());
    }

    public BinaryTreeNode<Key, Value> getLastNode() {
        return lastNodeOfSubtree(root);
    }

    private int numOfChildren(BinaryTreeNode<Key, Value> node) {
        int count = 0;
        if (node.getLeft() != null && !isExternal(node.getLeft()))
            count++;
        if (node.getRight() != null && !isExternal(node.getRight()))
            count++;
        return count;
    }

    private BinaryTreeNode<Key, Value> treeSearch(Key k, BinaryTreeNode<Key, Value> node) {
        if (isExternal(node))
            return node;

        int c = comparator.compare(k, node.getKey());

        if (c < 0)
            return treeSearch(k, node.getLeft());
        else if (c == 0)
            return node;
        else
            return treeSearch(k, node.getRight());
    }

    private void inOrderSubtree(BinaryTreeNode<Key, Value> node, List<BinaryTreeNode<Key, Value>> snapshot) {
        if (!isExternal(node)) {
            inOrderSubtree(node.getLeft(), snapshot);
            snapshot.add(node);
            inOrderSubtree(node.getRight(), snapshot);
        }
    }

    private BinaryTreeNode<Key, Value> createEmptyNode(BinaryTreeNode<Key, Value> parent) {
        return new BinaryTreeNode<>(null, null, parent, null, null);
    }

    private void collectGarbage(BinaryTreeNode<Key, Value> node) {
        node.setKey(null);
        node.setValue(null);
        node.setLeft(null);
        node.setRight(null);
    }
}
