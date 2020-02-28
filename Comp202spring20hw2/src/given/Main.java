package given;

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

import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;

import autograder.Autograder;
import autograder.MethodMap;
import code.ArrayDeque;
import code.LLDeque;
import code.Maze;
import code.Queue;
import code.Stack;
import given.Util.myVector;

public class Main {
  static Random rand = new Random();// Random(123456);

  static class generateRandomIntegers implements Autograder.GenerateInputParameter {
    Random rr;
    int maxInt;
    int minInt;

    generateRandomIntegers() {
      rr = new Random(System.currentTimeMillis());
      maxInt = 50;
      minInt = -50;
    }

    @Override
    public Integer[] generate() {
      Integer[] p = new Integer[1];
      p[0] = rr.nextInt(maxInt - minInt) + minInt;
      return p;
    }

    public void setSeed(long seed) {
      rr.setSeed(seed);
    }

  }
  
  private static boolean testArrayDequeCircularity() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

    boolean isCircular = true;
    
    ArrayDeque<Integer> aD = new ArrayDeque<Integer>(10);
    
    HashSet<Integer> ref = new HashSet<Integer>();
    for(int i = 5; i < 15; i++)
      ref.add(i);
    
    Integer[] refInts = {10,11,12,13,14,5,6,7,8,9};
    
    Field adA = aD.getClass().getDeclaredField("A");
    adA.setAccessible(true);
    Object[] tmp = (Object[]) adA.get(aD);
    for(int i = 0; i < 10; i++)
      aD.addBehind(i);
    for(int i = 0; i < 5; i++)
      aD.removeFront();
    for(int i = 10; i < 15; i++)
      aD.addBehind(i);
        
    HashSet<Object> adHS = getArrayDequeInternals(aD);
    if(!adHS.equals(ref)) {
      Autograder.Log("Internals do not match when testing for ArrayDeque circularity. Assuming non-circular.");
      isCircular = false;
    }
    else {
      for(int i = 0; i < tmp.length; i++)
        if(refInts[i] != tmp[i]) {
          Autograder.Log("ArrayDeque implementation not circular!");
          isCircular = false;
          break;
        }
    }
    
    aD = new ArrayDeque<Integer>(10);
    adA = aD.getClass().getDeclaredField("A");
    adA.setAccessible(true);
    for(int i = 0; i < 10; i++)
      aD.addFront(i);
    for(int i = 0; i < 5; i++)
      aD.removeBehind();
    for(int i = 10; i < 15; i++)
      aD.addFront(i);
    
    adHS = getArrayDequeInternals(aD);
    if(!adHS.equals(ref)) {
      Autograder.Log("Internals do not match when testing for ArrayDeque circularity. Assuming non-circular.");
      isCircular = false;
    }
    else {
      for(int i = 0; i < tmp.length; i++)
        if(refInts[i] != tmp[i]) {
          Autograder.Log("ArrayDeque implementation not circular!");
          isCircular = false;
          break;
        }
    }
    
    return isCircular;
  }

  private static float testArrayDequeResizing()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    float grade = 8;
    iDeque<Integer> arrayDequeSize = new ArrayDeque<Integer>(2);
    Field adA = arrayDequeSize.getClass().getDeclaredField("A");
    adA.setAccessible(true);
    Object[] tmp = (Object[]) adA.get(arrayDequeSize);
    if (tmp.length != 2) {
      Autograder.Log("Initial size not correct");
      grade--;
    }
    arrayDequeSize.addBehind(1);
    arrayDequeSize.addBehind(2);
    tmp = (Object[]) adA.get(arrayDequeSize);
    if (tmp.length != 2) {
      Autograder.Log("Early resizing");
      grade--;
    }
    arrayDequeSize.addBehind(3);
    tmp = (Object[]) adA.get(arrayDequeSize);
    if (tmp.length < 4) {
      Autograder.Log("Not resized enough or at all!");
      grade--;
    }
    arrayDequeSize.behind();
    arrayDequeSize.front();

    arrayDequeSize = new ArrayDeque<Integer>(500);
    tmp = (Object[]) adA.get(arrayDequeSize);
    if (tmp.length != 500) {
      Autograder.Log("Initial size not correct");
      grade--;
    }
    for (int i = 0; i < 1000; i++) {
      if (i % 2 == 0)
        arrayDequeSize.addBehind(i);
      else
        arrayDequeSize.addFront(i);

      if (i == 499) {
        tmp = (Object[]) adA.get(arrayDequeSize);
        if (tmp.length != 500) {
          Autograder.Log("Early resizing");
          grade--;
        }
      } else if (i == 500) {
        tmp = (Object[]) adA.get(arrayDequeSize);
        if (tmp.length != 1000) {
          Autograder.Log("Not resized enough or at all!");
          grade--;
        }
      }
    }

    tmp = (Object[]) adA.get(arrayDequeSize);
    if (tmp.length < 1000) {
      Autograder.Log("Not resized enough or at all!");
      grade--;
    }

    arrayDequeSize.addFront(-1);
    tmp = (Object[]) adA.get(arrayDequeSize);
    if (tmp.length < 2000) {
      Autograder.Log("Not resized enough or at all!");
      grade--;
    }

    return grade;
  }

  private static float simpleDequeChecks(iDeque<Integer> deque, String descriptor) {
    float grade = 0;
    if (deque.isEmpty())
      grade += 1;
    else
      Autograder.Log("Initial " + descriptor + " should be empty!");

    if (deque.size() == 0)
      grade += 1;
    else
      Autograder.Log("Initial " + descriptor + " should have zero size!");

    deque.addFront(2);
    deque.addFront(12);
    deque.addFront(17);
    deque.addBehind(9);
    deque.addFront(4);
    deque.addBehind(6);
    deque.removeFront();
    deque.removeBehind();

    if (!deque.isEmpty())
      grade += 1;
    else
      Autograder.Log(descriptor + " should not be empty after 6 additions and 2 removals");

    if (deque.size() == 4)
      grade += 1;
    else
      Autograder.Log("The size is not correct for " + descriptor + " after 6 additions and 2 removals");

    if (deque.front() == 17)
      grade += 1;
    else
      Autograder.Log("The front element of " + descriptor + " should be 17");

    if (deque.behind() == 9)
      grade += 1;
    else
      Autograder.Log("The behind element of " + descriptor + " should be 9");

    deque.clear();
    if (deque.isEmpty())
      grade += 1;
    else
      Autograder.Log(descriptor + " should be empty after a clear!");

    if (deque.size() == 0)
      grade += 1;
    else
      Autograder.Log(descriptor + " should have zero size after a clear!");

    if (deque.front() == null && deque.behind() == null && deque.removeBehind() == null && deque.removeFront() == null)
      grade += 4;
    else
      Autograder.Log("Empty " + descriptor + " should return null!");

    return grade / 2;
  }

  public static HashSet<Object> getArrayDequeInternals(ArrayDeque<?> ad)
      throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    Field adA = ad.getClass().getDeclaredField("A");
    adA.setAccessible(true);
    Object[] A = (Object[]) adA.get(ad);
    HashSet<Object> adHS = new HashSet<Object>();

    for (Object obj : A) {
      if (obj != null)
        adHS.add(obj);
    }

    return adHS;
  }
  
  public static HashSet<Object> getLLDequeInternals(LLDeque<?> ld) throws IllegalArgumentException,
      IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
    Class<?> nodeC = Class.forName(ld.getClass().getName() + "$Node");

    Field llH = ld.getClass().getDeclaredField("header");
    Field llT = ld.getClass().getDeclaredField("trailer");
    llH.setAccessible(true);
    llT.setAccessible(true);

    Field nn = nodeC.getDeclaredField("next");
    Field e = nodeC.getDeclaredField("element");
    nn.setAccessible(true);
    e.setAccessible(true);

    HashSet<Object> ldHS = new HashSet<Object>();

    Object tmp = nn.get(llH.get(ld));
    while (tmp != llT.get(ld)) {
      ldHS.add(e.get(tmp));
      tmp = nn.get(tmp);
    }

    return ldHS;
  }

  public static float testDeques() {
    float llGrade = 0;
    float adGrade = 0;
    String[] operationsArray = { "addFront", "addBehind", "removeFront", "removeBehind", "front", "behind", "size",
        "isEmpty", "clear" };

    myVector operationsProb = new myVector(9);
    operationsProb.set(0, 0.5f); // prob addFront
    operationsProb.set(1, 0.5f); // prob addBehind
    operationsProb.set(2, 0.4f); // prob removeFront
    operationsProb.set(3, 0.4f); // prob removeBehind
    operationsProb.set(4, 0.2f); // prob front
    operationsProb.set(5, 0.2f); // prob behind
    operationsProb.set(6, 0.1f); // prob size
    operationsProb.set(7, 0.1f); // prob isEmpty
    operationsProb.set(8, 0.01f); // prob clear
    operationsProb.normalize();

    // Reference implementation!
    iDeque<Integer> javaLL = new JavaLL<Integer>();

    ArrayDeque<Integer> arrayDeque = new ArrayDeque<Integer>();
    LLDeque<Integer> llDeque = new LLDeque<Integer>();

    try {
      float tmp = testArrayDequeResizing(); 
      adGrade += tmp;
    } catch (Exception e) {
      Autograder.Log("Something wrong with resizing");
    }

    adGrade += simpleDequeChecks(arrayDeque, "ArrayDeque"); 
    llGrade += simpleDequeChecks(llDeque, "LLDeque"); 

    // Recreating instead of calling clear in case it is not correctly implemented
    arrayDeque = new ArrayDeque<Integer>();
    llDeque = new LLDeque<Integer>();

    try {
      if (arrayDeque.toString() != "")
        Autograder.Log("Empty ArrayDeque should print an empty string!");
      else
        adGrade += 0.5;
    } catch (Exception e) {
      Autograder.Log("Exception caught trying to print an empty ArrayDeque!");
    }

    try {
      if (llDeque.toString() != "")
        Autograder.Log("Empty LLDeque should print an empty string!");
      else
        llGrade += 0.5;
    } catch (Exception e) {
      Autograder.Log("Exception caught trying to print an empty LLDeque!");
    }

    MethodMap mm = new MethodMap(iDeque.class);

    generateRandomIntegers gri = new generateRandomIntegers();
    mm.paramGenMap.put("addFront", gri);
    mm.paramGenMap.put("addBehind", gri);

    try {
      int a = 5;
      int l = 5;
      long t = System.currentTimeMillis();

      operationsProb.setSeed(t);
      gri.setSeed(t);
      int ad = Autograder.testDS(arrayDeque, javaLL, mm, operationsArray, operationsProb, 1000);
      if (ad != 0)
        Autograder.Log("Something wrong with returned values of ArrayDeque");
      Autograder.coc.start();
      System.out.println(arrayDeque);
      String adStr = Autograder.coc.stop().replace(System.lineSeparator(), "");
      javaLL.clear();

      operationsProb.setSeed(t);
      gri.setSeed(t);
      int ld = Autograder.testDS(llDeque, javaLL, mm, operationsArray, operationsProb, 1000);
      if (ld != 0)
        Autograder.Log("Something wrong with returned values of LLDeque");
      Autograder.coc.start();
      System.out.println(llDeque);
      String llStr = Autograder.coc.stop().replace(System.lineSeparator(), "");

      String refStr = javaLL.toString().replace(System.lineSeparator(), "");

      if (javaLL.isEmpty()) {
        refStr = "";
      }

      if (refStr.equals(adStr))
        adGrade += 2; 
      else
        Autograder.Log("toString of ArrayDeque is not front to back or does not match the desired format!");
      if (refStr.equals(llStr))
        llGrade += 0;
      else
        Autograder.Log("toString of LLDeque is not front to back or does not match the desired format! No points lost.");     
      
      a -= ad;
      if (a < 0)
        a = 0;
      adGrade += a * 1.5;  
      
      l -= ad;
      if (l < 0)
        l = 0;
      llGrade += l * 1.5;  

    } catch (Exception e) {
      e.printStackTrace();
    }

    // This part may change for our version of the autograder!
    HashSet<Integer> refElements = new HashSet<Integer>();

    for (Integer e : javaLL) {
      refElements.add(e);
    }

    try {
      HashSet<Object> adHS = getArrayDequeInternals(arrayDeque);

      if (adHS.equals(refElements))
        adGrade += 4;
      else
        Autograder.Log("ArrayDeque internals are not correct!");
    } catch (Exception e) {
      Autograder.Log("Encountered something wrong during checking the internals of the ArrayDequeue");
    }

    try {
      HashSet<Object> llHS = getLLDequeInternals(llDeque);

      if (llHS.equals(refElements))
        llGrade += 4;
      else
        Autograder.Log("LLDeque internals are not correct!");
    } catch (Exception e) {
      Autograder.Log("Encountered something wrong during checking the internals of the LLDeque");
      e.printStackTrace();
    }

    ArrayList<Integer> refList = new ArrayList<Integer>();

    for (Integer e : javaLL) {
      refList.add(e);
    }

    try {
      ArrayList<Integer> adList = new ArrayList<Integer>();

      for (Integer e : arrayDeque) {
        adList.add(e);
      }

      if (adList.equals(refList))
        adGrade += 4;
      else
        Autograder.Log("ArrayDeque iterator is not correct!");

    } catch (Exception e) {
      Autograder.Log("ArrayDeque iterator interface results in an exception");
    }

    try {
      ArrayList<Integer> llList = new ArrayList<Integer>();

      for (Integer e : llDeque) {
        llList.add(e);
      }

      if (llList.equals(refList))
        llGrade += 4;
      else
        Autograder.Log("LLDeque iterator is not correct!");
    } catch (Exception e) {
      Autograder.Log("LLDeque iterator interface results in an exception");
    }

    float adGradeScale = 0.0f;
    try {
      if(testArrayDequeCircularity())
        adGradeScale = 1.0f;
      else
        Autograder.Log("ArrayDeque implementation is not circular, getting zero from ArrayDeque tests.");
    } catch (Exception e) {
      Autograder.Log("Exception caught testing for ArrayDeque circularity, getting half points from ArrayDeque tests.");
    }
    
    return adGrade*adGradeScale+llGrade;
  }

  private static float simpleContainerChecks(iSimpleContainer<Integer> sc, String descriptor) {
    float grade = 0;
    if (sc.isEmpty())
      grade += 1;
    else
      Autograder.Log("Initial " + descriptor + " should be empty!");

    if (sc.size() == 0)
      grade += 1;
    else
      Autograder.Log("Initial " + descriptor + " should have zero size!");

    sc.push(5);
    sc.push(7);
    sc.push(8);
    sc.push(11);
    sc.push(14);
    sc.pop();

    if (!sc.isEmpty())
      grade += 1;
    else
      Autograder.Log(descriptor + " should not be empty after 5 additions and 1 removal");

    if (sc.size() == 4)
      grade += 1;
    else
      Autograder.Log("The size is not correct for " + descriptor + " after 5 additions and 1 removal");

    sc.clear();
    if (sc.isEmpty())
      grade += 1;
    else
      Autograder.Log(descriptor + " should be empty after a clear!");

    if (sc.size() == 0)
      grade += 1;
    else
      Autograder.Log(descriptor + " should have zero size after a clear!");

    sc.push(5);
    sc.push(7);
    sc.push(8);
    sc.push(11);
    sc.push(14);
    sc.pop();

    return grade / 2;
  }

  private static float testContainers() {
    float grade = 0;
    // Using our own deque!
    iSimpleContainer<Integer> stack = new Stack<JavaLL<Integer>, Integer>(new JavaLL<Integer>());
    iSimpleContainer<Integer> queue = new Queue<JavaLL<Integer>, Integer>(new JavaLL<Integer>());

    grade += simpleContainerChecks(stack, "Stack");
    grade += simpleContainerChecks(queue, "Queue");

    if (stack.peek() == 11)
      grade += 0.5;
    else
      Autograder.Log("The next element of stack should be 11");

    if (queue.peek() == 7)
      grade += 0.5;
    else
      Autograder.Log("The next element of stack should be 7");

    Integer[] integerList = new Integer[1000];

    // Creating new in case clear is not implemented correctly
    stack = new Stack<JavaLL<Integer>, Integer>(new JavaLL<Integer>());
    queue = new Queue<JavaLL<Integer>, Integer>(new JavaLL<Integer>());

    for (int i = 0; i < integerList.length; i++) {
      int a = rand.nextInt(50);
      integerList[i] = a;
      stack.push(a);
      queue.push(a);
    }
    int missed = 0;
    for (int i = 0; i < integerList.length; i++) {
      int s = stack.pop();
      int q = queue.pop();
      if (s != integerList[integerList.length - i - 1]) {
        missed++;
      }
      if (q != integerList[i]) {
        missed++;
      }
    }
    if (missed > 0) {
      Autograder.Log("The container input-output orderings are not correct");
    } else
      grade += 7;

    // May test with reference implementations and will not give those to you!

    return grade;
  }

  private static void loadMazeResults(JavaLL<Maze.Coordinate> refCoordinates, String fileName) throws Exception {
    refCoordinates.clear();

    BufferedReader br = new BufferedReader(new FileReader(fileName));
    String line = br.readLine().replace(" ", "");

    int i = line.indexOf('(');
    if (i == -1)
      throw new Exception("Wrong file format, no starting parenthesis");
    while (i != -1) {
      int j = line.indexOf(')', i + 1);
      if (j == -1)
        throw new Exception("Wrong file format, no matching parentheses");
      String[] coordinates = line.substring(i + 1, j).split(",");
      if (coordinates.length != 2) {
        ;
        throw new Exception("Wrong file format, something wrong in parentheses");
      }
      int x = Integer.parseInt(coordinates[0]);
      int y = Integer.parseInt(coordinates[1]);

      i = line.indexOf('(', j + 1);

      refCoordinates.addBehind(new Maze.Coordinate(x, y));
    }

    if (br.readLine() != null)
      throw new Exception("Wrong file format, extra line");
    br.close();
  }

  private static float testMazes() throws Exception {
    float grade = 0;

    // Using your implementation of stacks and queues but may use ours in the real
    // grading!
    Maze maze = new Maze();

    JavaLL<Maze.Coordinate> visitedDeque = new JavaLL<Maze.Coordinate>();

    iSimpleContainer<Maze.Coordinate> stack = new Stack<JavaLL<Maze.Coordinate>, Maze.Coordinate>(
        new JavaLL<Maze.Coordinate>());
    iSimpleContainer<Maze.Coordinate> queue = new Queue<JavaLL<Maze.Coordinate>, Maze.Coordinate>(
        new JavaLL<Maze.Coordinate>());

    JavaLL<Maze.Coordinate> refVisited = new JavaLL<Maze.Coordinate>();

    loadMazeResults(refVisited, "visited1.txt");
    stack.clear();
    visitedDeque.clear();
    maze.solveMaze(stack, visitedDeque, "maze1.txt");
    if (visitedDeque.equals(refVisited))
      grade += 1;
    else
      Autograder.Log("First maze test failed");

    loadMazeResults(refVisited, "visited2.txt");
    queue.clear();
    visitedDeque.clear();
    maze.solveMaze(queue, visitedDeque, "maze1.txt");
    if (visitedDeque.equals(refVisited))
      grade += 1;
    else
      Autograder.Log("Second maze test failed");

    loadMazeResults(refVisited, "visited3.txt");
    stack.clear();
    visitedDeque.clear();
    maze.solveMaze(stack, visitedDeque, "maze2.txt");
    if (visitedDeque.equals(refVisited))
      grade += 1;
    else
      Autograder.Log("Third maze test failed");

    loadMazeResults(refVisited, "visited4.txt");
    queue.clear();
    visitedDeque.clear();
    maze.solveMaze(queue, visitedDeque, "maze2.txt");
    if (visitedDeque.equals(refVisited))
      grade += 1;
    else
      Autograder.Log("Fourth maze test failed");

    loadMazeResults(refVisited, "visited5.txt");
    stack.clear();
    visitedDeque.clear();
    maze.solveMaze(stack, visitedDeque, "maze3.txt");
    if (visitedDeque.equals(refVisited))
      grade += 1;
    else
      Autograder.Log("Fifth maze test failed");

    loadMazeResults(refVisited, "visited6.txt");
    queue.clear();
    visitedDeque.clear();
    maze.solveMaze(queue, visitedDeque, "maze3.txt");
    if (visitedDeque.equals(refVisited))
      grade += 1;
    else
      Autograder.Log("Sixth maze test failed");

    loadMazeResults(refVisited, "visited7.txt");
    stack.clear();
    visitedDeque.clear();
    maze.solveMaze(stack, visitedDeque, "maze4.txt");
    if (visitedDeque.equals(refVisited))
      grade += 1;
    else
      Autograder.Log("Seventh maze test failed");

    loadMazeResults(refVisited, "visited8.txt");
    queue.clear();
    visitedDeque.clear();
    maze.solveMaze(queue, visitedDeque, "maze4.txt");
    if (visitedDeque.equals(refVisited))
      grade += 1;
    else
      Autograder.Log("Eighth maze test failed");

    return grade * 4;
  }

  public static void main(String[] args) {
    float grade = 0;

    Autograder.init();
    try {
      grade += testDeques(); // Max 54
    } catch (Exception e) {
      Autograder.Log("Unexpected exception while testing deques");
      e.printStackTrace();
    }
    try {
      grade += testContainers(); // Max 14
    } catch (Exception e) {
      Autograder.Log("Unexpected exception while testing containers");
      e.printStackTrace();
    }
    try {
      grade += testMazes(); // Max 32
    } catch (Exception e) {
      Autograder.Log("Unexpected exception while testing mazes");
      e.printStackTrace();
    }

    if (grade > 100)
      Autograder.Log("Grade is larger than expected, probably something is wrong!");

    Autograder.printLog();
    System.out.println("Grade: " + grade + "/100");
  }

}
