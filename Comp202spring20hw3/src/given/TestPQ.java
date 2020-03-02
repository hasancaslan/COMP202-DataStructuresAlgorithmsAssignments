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
 * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;

import code.ArrayBasedHeap;
import code.BSTBasedPQ;

public class TestPQ {

  public static long test(iAdaptablePriorityQueue<Integer, String> sellPQ, iAdaptablePriorityQueue<Integer, String> buyPQ, boolean output, String testFolderName) {

    sellPQ.setComparator(new DefaultComparator<Integer>());
    buyPQ.setComparator(new DefaultComparator<Integer>(true));

    File exchangeList = new File(testFolderName);

    int numberOfExchanges = exchangeList.list().length;

    Date startTime = new Date();
    for (int i = 0; i < numberOfExchanges; i++) {

      File exchange = new File(testFolderName + "/Exchange_" + i + ".txt");

      try (Scanner exchangeScanner = new Scanner(exchange)) {
        if (output)
          System.out.println("Iteration: " + i);
        while (exchangeScanner.hasNextLine()) {
          String nextAction = exchangeScanner.nextLine();
          String[] entry = nextAction.split(" ");
          switch (entry[1]) {
          case "Sell":
            if (output)
              System.out.println(entry[0] + " offered to sell for " + entry[2] + ".");
            sellPQ.insert(Integer.parseInt(entry[2]), entry[0]);
            break;
          case "Buy":
            if (output)
              System.out.println(entry[0] + " offered to buy for " + entry[2] + ".");
            buyPQ.insert(Integer.parseInt(entry[2]), entry[0]);
            break;
          case "Change":
            if (entry[2].equals("Sell")) {
              if (output)
                System.out.println(entry[0] + " changed his/her sell offer to " + entry[3] + ".");
              sellPQ.replaceKey(entry[0], Integer.parseInt(entry[3]));
            } else if (entry[2].equals("Buy")) {
              if (output)
                System.out.println(entry[0] + " changed his/her buy offer to " + entry[3] + ".");
              buyPQ.replaceKey(entry[0], Integer.parseInt(entry[3]));
            }
            break;
          case "Remove":
            if (entry[2].equals("Sell")) {
              if (output)
                System.out.println(entry[0] + " removed his/her sell offer of " + entry[3] + ".");
              sellPQ.remove(Integer.parseInt(entry[3]));
            } else if (entry[2].equals("Buy")) {
              if (output)
                System.out.println(entry[0] + " removed his/her buy offer of " + entry[3] + ".");
              buyPQ.remove(Integer.parseInt(entry[3]));
            }
            break;
          }
        }

        while (true) {
          if (!sellPQ.isEmpty() && !buyPQ.isEmpty()) {
            // System.out.println(sellPQ.top() + " " + buyPQ.top());
            if (sellPQ.top().getKey().compareTo(buyPQ.top().getKey()) <= 0) {
              System.out.println(sellPQ.top().getValue() + " sold to " + buyPQ.top().getValue() + " for " + sellPQ.top().getKey());
              sellPQ.pop();
              buyPQ.pop();
            } else
              break;
          } else
            break;
        }
      }

      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    return (new Date().getTime() - startTime.getTime());
  }
  
  public static void agTest(boolean pqTypeHeap, String testFolderName) {
    agTest(pqTypeHeap, testFolderName, true);
  }

  public static long agTest(boolean pqTypeHeap, String testFolderName, boolean verboseOutput) {

    if(pqTypeHeap)
      return test(new ArrayBasedHeap<Integer, String>(), new ArrayBasedHeap<Integer, String>(), verboseOutput, testFolderName);
    else
      return test(new BSTBasedPQ<Integer, String>(), new BSTBasedPQ<Integer, String>(), verboseOutput, testFolderName);
  }
  
  public static void testAll() throws IOException {
    PrintStream previous = System.out; 
    String[] folderNames = {"Exchange","Exchange2","Exchange3"};
    String[] pqNames = {"heap","tree"};
    for(String folder : folderNames) {
      for(String pq : pqNames) {
        File output = new File("student_"+pq+"_"+folder+"_output.txt");
        output.createNewFile();
        PrintStream fileOut = new PrintStream(output);
        System.setOut(fileOut);
        if(pq=="heap")
          agTest(true, folder, true);
        else
          agTest(false, folder, true);
      }
      System.setOut(previous);
    }
  }
  
  public static void main(String[] args) {
    String testFolderName = "Exchange"; // "Exchange2", "Exchange3"
    boolean verboseOutput = false;

    if (testFolderName.equals("Exchange"))
      verboseOutput = true;

    System.out.println("Heap based execution started.");
    long heapTime = agTest(true, testFolderName, verboseOutput);
    System.out.println("Heap based execution took " + heapTime + " miliseconds.");

    System.out.println();

    System.out.println("Tree based execution started.");
    long treeTime = agTest(true, testFolderName, verboseOutput);
    System.out.println("Tree based execution took " + treeTime + " miliseconds.");

  }
}
