package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 *
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {
    private BinaryTreeNode<Key, Value> parent;
    private BinaryTreeNode<Key, Value> left;
    private BinaryTreeNode<Key, Value> right;

    public BinaryTreeNode(Key k, Value v) {
        super(k, v);
    }

    public BinaryTreeNode(Key k, Value v, BinaryTreeNode<Key, Value> parent, BinaryTreeNode<Key, Value> leftChild, BinaryTreeNode<Key, Value> rightChild) {
        this(k, v);
        this.parent = parent;
        left = leftChild;
        right = rightChild;
    }

    public code.BinaryTreeNode<Key, Value> getLeft() {
        return left;
    }

    public void setLeft(code.BinaryTreeNode<Key, Value> left) {
        this.left = left;
    }

    public code.BinaryTreeNode<Key, Value> getRight() {
        return right;
    }

    public void setRight(code.BinaryTreeNode<Key, Value> right) {
        this.right = right;
    }

    public code.BinaryTreeNode<Key, Value> getParent() {
        return parent;
    }

    public void setParent(code.BinaryTreeNode<Key, Value> parent) {
        this.parent = parent;
    }
}
