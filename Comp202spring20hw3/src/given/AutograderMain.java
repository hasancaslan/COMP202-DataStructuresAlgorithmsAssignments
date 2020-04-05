package given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import autograder.Autograder;
import autograder.HeapAG;
import code.BSTBasedPQ;
import code.BinarySearchTree;
import code.BinaryTreeNode;

public class AutograderMain {
  
  private static Integer[] keys; 
  private static String[] values;
  private static String[] argMagic;
  private static Integer[] magic;
  private static int[] randomPermutation;
  private static int numItems;
  
  private static boolean isHeapDS(Object obj) {
    return (obj instanceof HeapAG);
  }
  
  private static boolean runIsHeap(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    Method curMet = obj.getClass().getMethod("isHeap");
    return (Boolean) curMet.invoke(obj);
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private static <Key, Value> boolean checkIfExists(Object obj, Entry<Key, Value> entry) {
    if(isHeapDS(obj)) {
      HeapAG<Key, Value> hag = (HeapAG) obj;
      return hag.checkIfExists(entry);
    } else {
      BinarySearchTree<Key, Value> bag = (BinarySearchTree) obj;
      boolean val = false;
      if(entry == null)
        return val;
      BinaryTreeNode<Key, Value> node = bag.getNode(entry.getKey());
      return entry.equals(node);
    }
  }
  
  private static <K> boolean compare(K[] arr1, HashSet<K> hs)
  {
    for(int i = 0; i < arr1.length; i++) {
      if(!hs.contains(arr1[i]))
        return false;
    }
    return arr1.length == hs.size();
  }
  
  private static <V> boolean compare(Integer[] keys, String[] values, List<BinaryTreeNode<Integer,String>> l, boolean isReversed)
  {
    if(keys.length != l.size())
      return false;
    for(int i = 0; i < keys.length; i++) {
      int key = keys[i];
      if (isReversed)
        key = -key;
      if(!l.get(i).equals(new BinaryTreeNode<Integer,String>(key, values[i]))) {
        return false;
      }
    }
    return true;
  }
  
  private static <K extends Comparable<K>, V> boolean inorderCheck(BinarySearchTree<K,V> bst, BinaryTreeNode<K, V> node) {
    boolean val = true;
    if(node == null)
      return val;
    
    BinaryTreeNode<K, V> lc = bst.getLeftChild(node);
    BinaryTreeNode<K, V> rc = bst.getRightChild(node);
    Comparator<K> c = bst.getComparator();
    if (!bst.isExternal(lc)) {
      if(c.compare(node.getKey(), lc.getKey())<0)
        return false;
      val = inorderCheck(bst, lc);
    }
    if (!bst.isExternal(rc)) {
      if(c.compare(node.getKey(), rc.getKey())>0)
        return false;
      val = inorderCheck(bst, rc) && val;
    }
    return val;
  }
  
  private static String[] magic(Integer[] keys, String[] values, Integer[] magic) {
    int min = keys[0];
    int max = keys[0];
    for(int j = 1; j < keys.length; j++) {
      if(min > keys[j]) {
        min = keys[j];
      }
      if(max < keys[j]) {
        max = keys[j];
      }
    }
    int k = max-min+1;
    int[] counts = new int[k];
    String[] argSorted = new String[values.length];
    for(int i = 0; i < values.length; i++) {
      counts[keys[i]-min]++; 
    }
    for(int i = 1; i < k; i++)
      counts[i] = counts[i] + counts[i-1];
    for(int i = values.length-1; i >= 0; i--) {
      argSorted[counts[keys[i]- min]-1] = values[i];
      magic[counts[keys[i]- min]-1] = keys[i];
      counts[keys[i]-min]--;
    }
    return argSorted;
  }
  
  private static int compareFiles(String fileName1, String fileName2)
  {
    int correctCount = 0;
    try {
      BufferedReader br1 = new BufferedReader(new FileReader(fileName1));
      BufferedReader br2 = new BufferedReader(new FileReader(fileName2));
      String line1, line2;

      while (true) {
        line1 = br1.readLine();
        line2 = br2.readLine();
        if(line1 == null || line2 == null){
          break;
        }  

        if(line1.equals(line2)){
          correctCount ++;
        }
      }

      br1.close();
      br2.close();
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
    return correctCount;
  }
  
  private static boolean sameKeyInserts(HeapAG<Integer,String> pq) { 
    pq.setComparator(new DefaultComparator<Integer>());
    pq.insert(1,"a");
    pq.insert(2,"b");
    pq.insert(3,"c");
    pq.insert(4,"d");
    pq.insert(1,"e");
    if(!pq.isHeap()) {
        Autograder.Log("Heap order property is not preserved after insertions while testing same key insertions. Must fix this to proceed with heap grading.");
        return false;
    }
    Entry<Integer, String> entry = pq.pop();
    boolean retVal = true;
    if(entry.getValue() != "a" && entry.getValue() != "e")
      retVal = false;
    if(entry.getValue() != "a" && entry.getValue() != "e")
      retVal = retVal && false;
    return retVal;
  }
  
  private static float bstQuickChecks() {
    float grade = 0;
    BinarySearchTree<Integer, String> bst = new BinarySearchTree<Integer, String>();
    bst.setComparator(new DefaultComparator<Integer>());
    
    if (bst.size()!=0) {
      Autograder.Log("Binary search tree should have 0 values when initialized. Must fix this to continue grading.");
      return grade;
    }
    
    if (!bst.isEmpty()) {
      Autograder.Log("Binary search tree isEmpty method should return true when initialized. Must fix this to continue grading.");
    }
    
    if (!bst.isExternal(bst.getRoot())) {
      Autograder.Log("Binary search tree root should be an external node when initialized. Must fix this to continue grading.");
      return grade;
    }
    
    if(bst.isInternal(bst.getRoot())) { 
      Autograder.Log("Binary search tree isInternal method did not return false for the root when initialized. Must fix this to continue grading.");
      return grade;
    }

    Integer[] smallKeys =  { 3 ,  6 ,  1 ,  8 ,  7 ,  5 ,  9 ,  4 ,  0 ,  2 }; 
    String[] smallValues = {"a", "b", "c", "d", "e", "i", "j", "k", "l", "m"};
    
    for(int i = 0; i < smallKeys.length; i++) {
      bst.put(smallKeys[i], smallValues[i]);
    }
        
    BinaryTreeNode<Integer, String> btn, btn2;
    
    btn = bst.getRoot();
    if(btn.getKey() == smallKeys[0] && btn.getValue() == smallValues[0])
      grade += 0.125;
    else {
      Autograder.Log("Binary search tree getRoot method did not return the correct root. Must fix this to continue grading.");
      return grade;
    }
    
    if(!bst.isInternal(btn)) { 
      Autograder.Log("Binary search tree isInternal method should return true for the root after multiple insertions. Must fix this to continue grading.");
      return grade;
    }
    
    if(bst.isRoot(btn))
      grade += 0.25;
    else
      Autograder.Log("Binary search tree isRoot method did not return true for the root");
    
    for(int i = 0; i < smallKeys.length; i++) {
      btn = bst.getNode(smallKeys[i]);
      
      if(btn.getKey() == smallKeys[i] && btn.getValue() == smallValues[i])
        grade += 0.5/smallKeys.length;
      else {
        Autograder.Log("Binary search tree getNode method did not return the correct node. Must fix this to continue grading.");
        return grade;
      }
      
     
      if(bst.isInternal(btn))
        grade += 0.5/smallKeys.length;
      else {
        if(btn.getKey() == 0 || btn.getKey() == 4 || btn.getKey() == 7 || btn.getKey() == 9) {
          if(bst.isExternal(btn))
            grade += 0.5/smallKeys.length;
        }
        else {
          Autograder.Log("Binary search tree isInternal method did not return true for an internal node. Must fix this to continue grading.");
          return grade;
        }
      }
      
      if(!bst.isExternal(btn))
        grade += 0.5/smallKeys.length;
      else {
        if(btn.getKey() == 0 || btn.getKey() == 4 || btn.getKey() == 7 || btn.getKey() == 9) {
          if(!bst.isInternal(btn))
            grade += 0.5/smallKeys.length;
        }
        else {
          Autograder.Log("Binary search tree isExternal method did not return false for an internal node. Must fix this to continue grading.");
          return grade;
        }
      }
      
      if(i != 0) {
        if(!bst.isRoot(btn)) {
          grade += 0.25/(smallKeys.length-1);
        } else {
          Autograder.Log("Binary search tree isRoot method did not return false for a non-root node. Must fix this to continue grading.");
          return grade;
        }
      } 
    }
    
    btn = bst.getRoot();
    if(bst.getParent(btn) == null)
      grade += 0.125;
    else
      Autograder.Log("The parent of the root must be null.");
    
    if(!bst.isLeftChild(btn))
      grade += 0.125;
    else
      Autograder.Log("The root should not be a left child.");
    
    if(!bst.isRightChild(btn))
      grade += 0.125;
    else
      Autograder.Log("The root should not be a right child.");
    
    if(bst.sibling(btn) == null)
      grade += 0.125;
    else
      Autograder.Log("The root does not have a sibling.");
    
    btn = bst.getNode(smallKeys[1]);
    if(bst.getParent(btn).getKey() == smallKeys[0])
      grade += 0.125;
    else
      Autograder.Log("The parent is not correct.");
    
    btn = bst.getNode(smallKeys[2]);
    if(bst.getParent(btn).getKey() == smallKeys[0])
      grade += 0.125;
    else
      Autograder.Log("The parent is not correct.");
    
    //sibling
    if(bst.sibling(btn).getKey() == smallKeys[1])
      grade += 0.25;
    else
      Autograder.Log("The sibling is not correct.");
    
    btn = bst.getNode(smallKeys[0]);
    btn2 = bst.getRightChild(btn);
    if(btn2.getKey() == smallKeys[1])
      grade += 0.25;
    else
      Autograder.Log("The right child is not correct.");
    if(bst.isRightChild(btn2)) 
      grade += 0.25;
    else
      Autograder.Log("The isRightChild method does not return true for a right child.");
    if(!bst.isLeftChild(btn2)) 
      grade += 0.25;
    else
      Autograder.Log("The isLeftChild method does not return false for a right child.");
    
    btn2 = bst.getLeftChild(btn);
    if(btn2.getKey() == smallKeys[2])
      grade += 0.25;
    else
      Autograder.Log("The left child is not correct.");
    if(!bst.isRightChild(btn2)) 
      grade += 0.25;
    else
      Autograder.Log("The isRightChild method does not return false for a left child.");
    if(bst.isLeftChild(btn2)) 
      grade += 0.25;
    else
      Autograder.Log("The isLeftChild method does not return true for a left child.");
        
    btn = bst.getNode(8);
    if(bst.sibling(btn).getKey() == 5)
      grade += 0.125;
    else
      Autograder.Log("Wrong sibling returned.");
    if(bst.getParent(btn).getKey() == 6)
      grade += 0.125;
    else
      Autograder.Log("Wrong parent returned.");
    if(bst.getRightChild(btn).getKey() == 9)
      grade += 0.25;
    else
      Autograder.Log("Wrong right child returned.");
    if(bst.getLeftChild(btn).getKey() == 7)
      grade += 0.25;
    else
      Autograder.Log("Wrong left child returned.");
    
    if(bst.remove(smallKeys[0]) != smallValues[0]) {
      Autograder.Log("Binary search tree remove did not return the correct value. Must fix this to continue grading.");
      return grade;
    }
    
    btn = bst.getRoot();
    if(btn.getKey() == smallKeys[0]-1 || btn.getKey() == smallKeys[0]+1 || bst.isRoot(btn)) {
      grade += 0.125;
    } else {
      Autograder.Log("The root is not correct after the removal of the previous root. Must fix this to continue grading.");
      return grade;
    }
        
    //floor
    btn = bst.floor(smallKeys[0]);
    if(btn == null) 
      Autograder.Log("Floor should not be null when it is in the binary search tree.");
    else {
      if(btn.getKey() == smallKeys[0]-1) {
        grade += 0.5;
      } else 
        Autograder.Log("The wrong floor is returned.");
    }
    btn = bst.floor(-1);
    if(btn == null) 
      grade += 0.25;
    else
      Autograder.Log("Floor should be null when it does not exist in the binary search tree.");
    
    //ceiling
    btn = bst.ceiling(smallKeys[0]);
    if(btn == null) 
      Autograder.Log("Ceiling should not be null when it is in the binary search tree.");
    else {
      if(btn.getKey() == smallKeys[0]+1) {
        grade += 0.5;
      } else 
        Autograder.Log("The wrong ceiling is returned.");
    }
    btn = bst.ceiling(smallKeys.length);
    if(btn == null) 
      grade += 0.25;
    else
      Autograder.Log("Ceiling should be null when it does not exist in the binary search tree.");
    
    return grade;
  }
  
  private static float _testBST(boolean isReversed) {
    float grade = 0;
    String dsName = "Binary Search Tree";
    BinarySearchTree<Integer, String> bst = new BinarySearchTree<Integer, String>();
    bst.setComparator(new DefaultComparator<Integer>(isReversed));
    
    if (bst.size()==0) {
      grade += 0.2;
    } else {
      Autograder.Log(dsName + " should have 0 values when initialized.");
    }
    
    if (bst.isEmpty()) {
      grade += 0.125;
    } else {
      Autograder.Log(dsName + " isEmpty method should return true when initialized.");
    }
    
    if (bst.isExternal(bst.getRoot())) {
      grade += 0.125;
    } else {
      Autograder.Log(dsName + " root should be an external node when initialized.");
    }
    
    if(!bst.isInternal(bst.getRoot())) { 
      grade += 0.125;
    } else {
      Autograder.Log("Binary search tree isInternal method did not return false for the root when initialized.");
      return grade;
    }
    
    List<BinaryTreeNode<Integer,String>> nodeList = bst.getNodesInOrder();
    if(nodeList == null) {
      Autograder.Log(dsName + " getNodesInOrder method must return an empty list instead of null when initialized.");
    }
    else {
      if(nodeList.size() == 0)
        grade += 0.125;
      else
        Autograder.Log(dsName + " getNodesInOrder method must return an empty iterable when initialized.");
    }

    for(int i = 0; i < keys.length; i++) {
      String tmp;
      if(isReversed)
        tmp = bst.put(-keys[i], values[i]);
      else 
        tmp = bst.put(keys[i], values[i]);
      
      if (tmp == null) {
        grade += 0.25/keys.length;
      }
      else {
        Autograder.Log(dsName + " must return null if the key is put for the first time.");
      }
      
      if (bst.size() == i+1) {
        grade += 0.2/keys.length;
      }
      else {
        Autograder.Log(dsName + " does not have the correct size after insertions.");
      }
    }
    
    HashSet<Integer> returnedKeys = new HashSet<Integer>();
    try {
      Iterable<Integer> keyIterable = bst.keySet();
      if(keyIterable == null) {
        Autograder.Log("Binary search tree keySet method should not return null.");
      }
      else {
        for(Integer item : keyIterable) {
          if(isReversed)
            returnedKeys.add(-item);
          else
            returnedKeys.add(item);
        }
        if(compare(keys, returnedKeys) ) { //returnedKeys.equals(keys)) {
          grade += 0.5;
        } else {
          Autograder.Log("The iterable returned by the binary search tree keySet method does not have all the keys.");
        }
      }
    } catch(Exception e) {
      Autograder.Log("Exception while getting the keySet of the binary search tree.");
    }
    
    if (!bst.isEmpty()) {
      grade += 0.25;
    } else {
      Autograder.Log(dsName + " should not be empty after puts.");
    }
    
    int tmp = 0;
    
    if (!bst.isExternal(bst.getRoot())) { 
      grade += 0.25;
    } else {
      Autograder.Log("Non-empty tree should not return an external root.  Must fix this to proceed with binary search tree grading");
      return grade;
    }
    
    if(bst.isInternal(bst.getRoot())) {
      grade += 0.25;
    } else {
      Autograder.Log("Non-empty tree should return an internal root.  Must fix this to proceed with binary search tree grading");
      return grade;
    }
    
    if(inorderCheck(bst, bst.getRoot())) {
      grade += 1;
    } else {
      String str = "";
      if(isReversed)
        str = "Error could be due to not using or returning the correct comparator.";
      Autograder.Log("This is not a binary search tree. Must fix this to proceed with grading." + str);
      return grade;
    }
    
    if(bst.get(numItems*10+1) == null) {
      grade += 0.25;
    } else {
      Autograder.Log("Binary search tree get method should return null when the key is not in the data structure.");
    }
    
    if(bst.getValue(numItems*10+1) == null) {
      grade += 0.25;
    } else {
      Autograder.Log("Binary search tree getValue method should return null when the key is not in the data structure.");
    }
    
    BinaryTreeNode<Integer, String> btn = bst.getNode(numItems*10+1);
    if(bst.isExternal(btn)) {
      if(btn != null) {
        BinaryTreeNode<Integer, String> btnM;
        if(isReversed)
          btnM = bst.ceiling(numItems*10+1);
        else 
          btnM = bst.floor(numItems*10+1);
        if(btnM.equals(bst.getParent(btn)))
          grade += 0.5;
        else {
          Autograder.Log("The dummy node returned by the Binary search tree getNode method should be at its correct location.");
        }
      } else
        grade += 0.5;
    } else {
      Autograder.Log("Binary search tree getNode method should return an external node when the key is not in the data structure.");
    }
    
    for(int i = 0; i < keys.length; i++) {
      tmp = bst.size();
      int key = keys[i];
      if(isReversed)
        key = -key;
      String val = bst.get(key);
      if (val == values[i]) {
        grade += 0.75/keys.length;
      } else {
        Autograder.Log("The correct value is not returned for the given key with the get method in  " + dsName + " grading. Must fix this to proceed with grading.");
        return grade;
      }
      if (val == bst.getValue(key)) {
        grade += 0.75/keys.length;
      } else {
        Autograder.Log("The correct value is not returned for the given key with the getValue method in  " + dsName + " grading.");
        return grade;
      }
      if(bst.size() == tmp) {
        grade += 0.2/keys.length;
      } else {
        Autograder.Log("Binary search tree size should not change after get.");
      }
    }
    
    int key = keys[randomPermutation[numItems/2]];
    if(isReversed)
      key = -key;
    String originalValue = bst.get(key);
    boolean b = bst.put(key, "abc") == originalValue;
    b = b && bst.put(key, values[randomPermutation[numItems/2]]) == "abc";
    if(b) {
      grade += 0.5;
    } else {
      Autograder.Log("Binary search tree must return the old value if an existing entry's key is the same as the new key being put.");
    }
    
    if(bst.remove(numItems*10+1) == null) {
      grade += 0.25;
    } else {
      Autograder.Log("Binary search tree remove method should return null when the key is not in the data structure.");
    }
    
    nodeList = bst.getNodesInOrder();
    if(nodeList == null) {
      Autograder.Log(dsName + " getNodesInOrder method must return a list instead of null.");
    }
    else {
      if(compare(magic, argMagic, nodeList, isReversed))
        grade += 1.0;
      else
        Autograder.Log(dsName + " getNodesInOrder method must return a list where the nodes are in correct order.");
    }
    
    for(int i = 0; i < keys.length; i++) {
      int j = randomPermutation[i];
      tmp = bst.size();
      key = keys[j];
      if(isReversed)
        key = -key;
      String val = bst.remove(key);
      if (val == values[j]) {
        grade += 2.25/keys.length;
      } else {
        Autograder.Log("The correct value is not returned for the given key with the remove method in  " + dsName + " grading.");
        return grade;
      }
      if(bst.size() == tmp-1) {
        grade += 0.2/keys.length;
      } else {
        Autograder.Log(dsName + " size should decrease after removal.");
      }
      if(i < keys.length-1) {
        if(inorderCheck(bst, bst.getRoot())) {
          grade += 1./(keys.length-1);
        } else {
          Autograder.Log("This is not a binary search tree. Must fix this to proceed with binary search tree grading.");
          return grade;
        }
      }
    }
    
    if(bst.size() == 0) {
      grade += 0.2;
    } else {
      Autograder.Log(dsName + " size should be 0 after removing all the values.");
    }
    
    if(bst.isEmpty()) {
      grade += 0.125;
    } else {
      Autograder.Log(dsName + " should be empty after removing all the values.");
    }
    
    if (bst.isExternal(bst.getRoot())) {
      grade += 0.125;
    } else {
      Autograder.Log(dsName + " root should be an external node when empty.");
    }
    
    if(!bst.isInternal(bst.getRoot())) {
      grade += 0.125;
    } else {
      Autograder.Log(dsName + " root should not be an internal node when empty.");
      return grade;
    }
    
    nodeList = bst.getNodesInOrder();
    if(nodeList == null) {
      Autograder.Log(dsName + " getNodesInOrder method must return an empty list instead of null when empty.");
    }
    else {
      if(nodeList.size() == 0)
        grade += 0.125;
      else
        Autograder.Log(dsName + " getNodesInOrder method must return an empty iterable when empty.");
    }
    
    return grade;
  }
  
  private static float testBST() { 
    float grade = 0;
    
    try {
      grade += bstQuickChecks();
      grade += _testBST(false);
      grade += _testBST(true)/2;
    } catch (Exception e) {
      Autograder.Log("Exception caught while testing binary search tree methods.");
      e.printStackTrace();
    }
    
    return grade;
  }
 
  private static float pqBasics(iAdaptablePriorityQueue<Integer,String> pq, boolean isMax, String dsName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    float grade = 0;
    pq.setComparator(new DefaultComparator<Integer>(isMax));
    
    if (pq.size() == 0) {
      grade += 0.2;
    } else {
      Autograder.Log(dsName + " should have 0 values when initialized.");
    }
    
    if (pq.isEmpty()) {
      grade += 0.125;
    } else {
      Autograder.Log(dsName + " isEmpty method should return true when initialized.");
    }
    
    if (pq.top() == null) {
      grade += 0.25;
    } else {
      Autograder.Log(dsName + " top method should return null when initialized.");
    }
    
    if (pq.pop() == null) {
      grade += 0.25;
    } else {
      Autograder.Log(dsName + " pop method should return null when initialized.");
    }
    
    
    int numPopped = 0;
    
    for(int i = 0; i < keys.length; i++) {
      if(isMax)
        pq.insert(-keys[i], values[i]);
      else 
        pq.insert(keys[i], values[i]);
      
      if (pq.size() == i+1) {
        grade += 0.2/keys.length;
      }
      else {
        Autograder.Log(dsName + " does not have the correct size after insertions.");
      }
      if(isHeapDS(pq)) {
        if(runIsHeap(pq)) {
          grade += 2./keys.length;
        } else {
          Autograder.Log("Heap order property is not preserved after insertion. Must fix this to proceed with heap grading.");
          return grade;
        }
      }
    }
    
    if (!pq.isEmpty()) {
      grade += 0.25;
    } else {
      Autograder.Log(dsName + " should not be empty after insertions.");
    }
    
    int tmp; 
    Entry<Integer,String> entry;
    
    for(int i = 0; i < keys.length; i++) {
      tmp = pq.size();
      entry = pq.top();
      if (entry.getValue() == argMagic[numPopped]) {
        grade += 1./keys.length;
      } else {
        Autograder.Log("The top element is not the minimum element. Must fix this to proceed with " + dsName + " grading.");
        return grade;
      }
      if(pq.size() == tmp) {
        grade += 0.2/keys.length;
      } else {
        Autograder.Log("Size should not change after checking top.");
      }
      
      tmp = pq.size();
      entry = pq.pop();
      if (entry.getValue() == argMagic[numPopped]) {
        grade += 2.5/keys.length;
        numPopped++;
      } else {
        Autograder.Log("The popped element is not the minimum element. Must fix this to proceed with " + dsName + " grading.");
        return grade;
      }
      if(pq.size() == tmp-1) {
        grade += 0.2/keys.length;
      } else {
        Autograder.Log(dsName + " size should decrease after popping.");
      }
      if(isHeapDS(pq)) {
        if(runIsHeap(pq)) {
          grade += 2./keys.length;
        } else {
          Autograder.Log("Heap order property is not preserved after insertion. Must fix this to proceed with heap grading.");
        return grade;
        }
      }
    }
    
    if(pq.size() == 0) {
      grade += 0.2;
    } else {
      Autograder.Log(dsName + " size should be 0 after popping all the values.");
    }
    
    if(pq.isEmpty()) {
      grade += 0.125;
    } else {
      Autograder.Log(dsName + " should be empty after popping all the values.");
    }
    
    if (pq.top() == null) {
      grade += 0.25;
    } else {
      Autograder.Log(dsName + " top method should return null when empty.");
    }
    
    if (pq.pop() == null) {
      grade += 0.25;
    } else {
      Autograder.Log(dsName + " pop method should return null when empty.");
    }
    
    return grade;
  }
  
  private static float apqMethods(iAdaptablePriorityQueue<Integer,String> pq, String dsName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    float grade = 0;
    pq.setComparator(new DefaultComparator<Integer>());
    if(pq.remove(1) == null) {
      grade += 0.25;
    } else {
      Autograder.Log(dsName + " remove method should return null when empty.");
    }
    
    for(int i = 0; i < keys.length; i++)
      pq.insert(keys[i], values[i]);
       
    if(pq.remove(-1) == null) {
      grade += 0.25;
    } else {
      Autograder.Log(dsName + " remove method should return null when the key is not in the PQ.");
    }
  
    if(pq.replaceKey(new Entry<Integer,String>(DataGenerator.randomIntRange(1, keys.length-1),"a"), 0) == null)
      grade += 0.25;
    else
      Autograder.Log(dsName + " replaceKey(Entry, ...) method should return null if the entry is not in the PQ");
    
    if(pq.replaceKey("a", 0) == null)
      grade += 0.25;
    else
      Autograder.Log(dsName + " replaceKey(Value, ...) method should return null if the value is not in the PQ");
  
    if(pq.replaceValue(new Entry<Integer,String>(DataGenerator.randomIntRange(1, keys.length-1),"a"), "b") == null)
      grade += 0.25;
    else
      Autograder.Log(dsName + " replaceKey(Entry, ...) method should return null if the entry is not in the PQ");
    
    HashSet<Integer> removedKeys = new HashSet<Integer>();
    
    //remove
    float gradeTmp = 0;
    int count = 0;
    for(int i = 0; i < keys.length; i += DataGenerator.randomIntRange(1, 3)) {
      count++;
      String tmp = pq.remove(keys[i]);
      removedKeys.add(i);
      if(tmp == null) {
        Autograder.Log(dsName + " remove method should not return null when the key is in the PQ.");
      } else {
        if (tmp == values[i]) {
          gradeTmp += 1.5;
        } else {
          Autograder.Log(dsName + " remove method should return the correct value.");
        }
      }
    }
    
    if(isHeapDS(pq)) {
      if(runIsHeap(pq)) {
        grade += 0.5;
      } else {
        Autograder.Log("Heap order property is not preserved after removes.");
      }
    }
    
    if(count != 0)
      grade += gradeTmp/count;
        
    //replaceKey(Entry, ...)
    gradeTmp = 0;
    count = 0;
    for(int i = 0; i < keys.length; i += DataGenerator.randomIntRange(1, 3)) {
      count++;
      Entry<Integer,String> entry = new Entry<Integer,String>(keys[i],values[i]);
      Integer tmp = pq.replaceKey(entry, -keys[i]);
      if(removedKeys.contains(i)) {
        if(tmp == null)
        {
          gradeTmp += 1.25;
        } else {
          Autograder.Log(dsName + " replaceKey(Entry, ...) method should return null when the entry is not in the PQ.");
        }
      } else {
        if(tmp == null) {
          Autograder.Log(dsName + " replaceKey(Entry, ...) method should not return null when the entry is in the PQ.");
        } else if(tmp == keys[i]) {
          entry.setKey(-keys[i]);
          if(checkIfExists(pq, entry)) {
            gradeTmp += 1.25;
          } else {
            Autograder.Log(dsName + " replaceKey(Entry, ...) method did not change the key or also changed the value.");
          }
        } else {
          Autograder.Log(dsName + " replaceKey(Entry, ...) method should return the older key." + tmp + " vs " + keys[i]);
        }
      }
    }
        
    if(isHeapDS(pq)) {
      if(runIsHeap(pq)) {
        grade += 0.5;
      } else {
        Autograder.Log("Heap order property is not preserved after replaceKeys.");
      }
    }
      
    if(count != 0)
      grade += gradeTmp/count;
    
    //replaceKey(Value, ...)
    gradeTmp = 0;
    count = 0;
    for(int i = 0; i < keys.length; i += DataGenerator.randomIntRange(1, 3)) {
      count++;
      Integer tmp = pq.replaceKey(values[i], -keys[i]);
      if(removedKeys.contains(i)) {
        if(tmp == null)
        {
          gradeTmp += 1.25;
        } else {
          Autograder.Log(dsName + " replaceKey(Value, ...) method should return null when the key is not in the PQ.");
        }
      } else {
        if(tmp == null) {
          Autograder.Log(dsName + " replaceKey(Value, ...) method should not return null when the value is in the PQ." + values[i] + " " + keys[i]);
        }
        else if(tmp == keys[i] || tmp == -keys[i])
        {
          if(checkIfExists(pq, new Entry<Integer,String>(-keys[i], values[i]))) {
            gradeTmp += 1.25;
          } else {
            Autograder.Log(dsName + " replaceKey(Value, ...) method did not change the key or also changed the value.");
          }
        } else {
          Autograder.Log(dsName + " replaceKey(Value, ...) method should return the older key." + tmp + " vs " + keys[i]);
        }
      }
    }
    
    if(isHeapDS(pq)) {
      if(runIsHeap(pq)) {
        grade += 0.5;
      } else {
        Autograder.Log("Heap order property is not preserved after replaceKeys.");
      }
    }
      
    if(count != 0)
      grade += gradeTmp/count;
    
    //replaceValue
    gradeTmp = 0;
    count = 0;
    for(int i = 0; i < keys.length; i += DataGenerator.randomIntRange(1, 3)) {
      count++;
      String newVal = DataGenerator.randomString(5, 10);
      String tmp = pq.replaceValue(new Entry<Integer,String>(keys[i],values[i]), newVal);
      if(removedKeys.contains(i)) {
        if(tmp == null)
        {
          gradeTmp += 0.75;
        } else {
          Autograder.Log(dsName + " replaceValue method should return null when the entry is not in the PQ.");
        }
      } else {
        int key = keys[i];
        if(tmp == null) {
          key = -key;
          tmp = pq.replaceValue(new Entry<Integer,String>(key, values[i]), newVal);
        }
        if(tmp == values[i])
        {
          if(checkIfExists(pq, new Entry<Integer,String>(key, newVal))) {
            gradeTmp += 0.75;
          } else {
            Autograder.Log(dsName + " replaceValue method did not change the value or also changed the key.");
          }
        } else {
          Autograder.Log(dsName + " replaceValue method should return the older value." + tmp + " vs " + values[i]);
        }
      }
    }
    
    if(isHeapDS(pq)) {
      if(runIsHeap(pq)) {
        grade += 0.5;
      } else {
        Autograder.Log("Heap order property is not preserved after replaceKeys.");
      }
    }
      
    if(count != 0)
      grade += gradeTmp/count; 
    
    return grade;
  }
  
  
  private static float testABH() {
    float grade = 0;
    HeapAG<Integer, String> abh;
    
    try {
      abh = new HeapAG<Integer, String>();
      grade += pqBasics(abh, false, "Array Based Heap");
      abh = new HeapAG<Integer, String>();
      grade += pqBasics(abh, true, "Max Array Based Heap")/2;
      abh = new HeapAG<Integer, String>();
      if(sameKeyInserts(abh))
        grade += 1.;
      else
        Autograder.Log("Array Based Heap must allow multiple entries with the same key.");
    } catch (Exception e) {
      Autograder.Log("Exception caught while testing array based heap priority queue basics.");
      e.printStackTrace();
    }
    
    try {
      abh = new HeapAG<Integer, String>();
      grade += apqMethods(abh, "Array Based Heap");
    } catch (Exception e) {
      Autograder.Log("Exception caught while testing array based heap adaptable priority queue basics.");
      e.printStackTrace();
    }
    
    return grade;
  }
  
  private static float testBstPQ() {
    float grade = 0;
    BSTBasedPQ<Integer, String> bpq;
    
    try {
      bpq = new BSTBasedPQ<Integer, String>();
      grade += pqBasics(bpq, false, "BST Based Heap")/2;
      bpq = new BSTBasedPQ<Integer, String>();
      grade += pqBasics(bpq, true, "Max BST Based Heap")/2;
    } catch (Exception e) {
      Autograder.Log("Exception caught while testing bst based priority queue basics.");
      e.printStackTrace();
    }
    
    try {
      bpq = new BSTBasedPQ<Integer, String>();
      grade += apqMethods(bpq, "BST Based PQ");
    } catch (Exception e) {
      Autograder.Log("Exception caught while testing bst based adaptable priority queue basics.");
      e.printStackTrace();
    }
    
    return grade;
  }
  
  public static float testOrderBook() throws IOException {
    float exchangeGrade = 0;
    PrintStream previous = System.out; 
    String[] folderNames = {"Exchange2","Exchange3"};
    String[] pqNames = {"heap","tree"};
    for(String folder : folderNames) {
      String referenceFileName = folder+"_output.txt";
      for(String pq : pqNames) {
        String outputFileName = "student_"+pq+"_"+referenceFileName;
        File output = new File(outputFileName);
        output.createNewFile();
        PrintStream fileOut = new PrintStream(output);
        System.setOut(fileOut);
        if(pq=="heap")
          TestPQ.agTest(true, folder, false);
        else
          TestPQ.agTest(false, folder, false);
        System.setOut(previous);
        exchangeGrade += compareFiles(referenceFileName,outputFileName);
      }   
    }
    return exchangeGrade;
  }
  
  
  public static void main(String[] args) {
    float grade = 0;
    Autograder.init();
    Autograder.setLocationReporting(true);
    
    // Testing priority queues
    numItems = 100;
    keys = DataGenerator.randomIntRangeUnique(0, numItems*10, numItems);
    values =  DataGenerator.randomString(5, 10, numItems);
    magic = new Integer[keys.length];
    argMagic = magic(keys, values, magic);
    randomPermutation = Util.randomPermutation(keys.length);
    
    try {
      grade += testBST(); 
    } catch (Exception e) {
      Autograder.Log("Unexpected exception while testing the binary search tree base heap");
      e.printStackTrace();
    }
    
    try {
      grade += testABH(); 
    } catch (Exception e) {
      Autograder.Log("Unexpected exception while testing the array base heap");
      e.printStackTrace();
    }
    
    try {
      grade += testBstPQ(); 
    } catch (Exception e) {
      Autograder.Log("Unexpected exception while testing the binary search tree based priority queue");
      e.printStackTrace();
    }
    
    try {
      grade += testOrderBook()/1516*19;      
    } catch (Exception e) {
      Autograder.Log("Unexpected exception while going over the order-book test");
      e.printStackTrace();
    }
    
    try {
      GradePhoneBook.main(args);
      grade += (compareFiles("phonebook_output.txt","student_phonebook_output.txt")/(942-61))*20;
    } catch (Exception e) {
      Autograder.Log("Unexpected exception while testing phonebook functionality");
      e.printStackTrace();
    }
    
    
    
    Autograder.printLog();
    System.out.printf("Grade:  %.2f /100" + System.lineSeparator(),grade);
  }

}
