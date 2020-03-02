package given;

import java.util.Comparator;

/*
 * Copyright 2018 Baris Akgun
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of 
 * conditions and the following disclaimer in the documentation and/or other materials provided 
 * with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to 
 * endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Most importantly, this software is provided for educational purposes and should not be used for
 * anything else.
 * 
 * AUTHORS: Baris Akgun
 *
 * DO NOT MODIFY 
 * 
 * */

import java.util.List;
import code.BinaryTreeNode;

/*
 * Interface representing a generic binary tree ADT. 
 * 
 * Note that this is by itself not too useful since there are no
 * methods to build the tree!
 * 
 */
public interface iBinarySearchTree<Key, Value> {

  // Returns the number of nodes in the tree
  public int size();

  // Returns true if the tree contains no nodes; false otherwise
  public boolean isEmpty();

  // Returns the root node of the tree
  public BinaryTreeNode<Key, Value> getRoot();

  // Returns the parent of the given node
  public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node);

  // Returns true if the given node is an internal node
  public boolean isInternal(BinaryTreeNode<Key, Value> node);

  // Returns true if the given node is an external node
  public boolean isExternal(BinaryTreeNode<Key, Value> node);

  // Returns true if the given node is the root node
  public boolean isRoot(BinaryTreeNode<Key, Value> node);

  // Returns the node with the given key if it is in the tree
  // Returns null if there is no node with the given key in the tree
  public BinaryTreeNode<Key, Value> getNode(Key k);

  // Returns the value of the node with the given key if it is in the tree
  // Returns null if there is no node with the given key in the tree
  public Value getValue(Key k);

  // Returns the left child of the given node
  // Returns null if the left child does not exist
  public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node);

  // Returns the right child of the given node
  // Returns null if the right child does not exist
  public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node);

  // Returns the sibling of the given node
  // Returns null if the sibling does not exist
  public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node);

  // Returns true if the node is the left child of its parent node, false
  // otherwise
  public boolean isLeftChild(BinaryTreeNode<Key, Value> node);

  // Returns true if the node is the right child of its parent node, false
  // otherwise
  public boolean isRightChild(BinaryTreeNode<Key, Value> node);

  // Returns the nodes of the tree based on an in-order traversal
  public List<BinaryTreeNode<Key, Value>> getNodesInOrder();

  // Sets the comparator to be used in comparisons
  public void setComparator(Comparator<Key> C);

  // Returns the comparator to be used in comparisons
  public Comparator<Key> getComparator();

  // Returns the node with the smallest key that is larger than or equal to k
  // Returns null if ceiling does not exist
  public BinaryTreeNode<Key, Value> ceiling(Key k);

  // Returns the node with the largest key that is smaller than or equal to k
  // Returns null if floor does not exist
  public BinaryTreeNode<Key, Value> floor(Key k);

}
