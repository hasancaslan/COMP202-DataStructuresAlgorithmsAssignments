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
 * AUTHORS: Baris Akgun, Muhammad Nufail Farooqi
 *
 * DO NOT MODIFY 
 * 
 * */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import autograder.*;

import code.*;

public class Test {

  public static boolean verbose;

  public static boolean TestCounter(int h) {
    // Simple Tests
    String S1 = "once twice thrice thrice twice thrice";
    String S2 = "Today is a lovely day";
    String S3 = "The dog and the cat were outside the store";
    String S4 = "How much wood would a woodchuck chuck if a woodchuck could chuck wood?";
    String S5 = "Guzel ile guzel guzel yemek yemek guzeldir";

    String[] allS = { S1, S2, S3, S4, S5 };
    HashCounter<String> myCounter;
    for (String S : allS) {
      if (verbose) {
        System.out.println("The sentence: ");
        System.out.println(S);
        System.out.println("The counter:");
      }
      String[] record = S.toString().replaceAll("[^A-Za-z0-9 ]+", "").toLowerCase().split("\\s+");
      if (h == 0)
        myCounter = new HashCounter<String>(new HashMapSC<String, Integer>());
      else
        myCounter = new HashCounter<String>(new HashMapDH<String, Integer>());
      myCounter.countAll(record);
      if (verbose)
        Util.print(myCounter);
    }

    // Test counts for files in News folder
    boolean countTest = true;
    String words = "";

    BufferedReader fileReader = null;

    File dir = new File("News");
    File[] files = dir.listFiles();
    String[] fnames = new String[files.length];
    for (int i = 0; i < files.length; i++) {
      fnames[i] = files[i].getName();
      Path path = Paths.get("News/" + fnames[i]);
      try {
        words = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
      } catch (IOException e) {
        System.out.println("Error Reading file: " + path.toString());
        e.printStackTrace();
        System.exit(0);
      }
      String[] record = words.toString().replaceAll("[^A-Za-z0-9� ]+", "").toLowerCase().split("\\s+");
      if (h == 0)
        myCounter = new HashCounter<String>(new HashMapSC<String, Integer>());
      else
        myCounter = new HashCounter<String>(new HashMapDH<String, Integer>());
      myCounter.countAll(record);
      try {
        fileReader = new BufferedReader(new FileReader("NewsCounts/" + fnames[i].replace(".txt", "_count.csv")));
        String line = "";
        while ((line = fileReader.readLine()) != null) {
          String[] tokens = line.split(",");
          String k = tokens[0];
          Integer v = Integer.parseInt(tokens[1]);
          if (myCounter.getCount(k) != v) {
            if (verbose)
              System.out.println("Invalid count for key : " + k);
            Autograder.Log("Invalid count for key : " + k + " in TestCounter");
            countTest = false;
            break;
          }
        }
      } catch (IOException e) {
        System.out.println("Error Reading file : " + "NewsCounts/" + fnames[i].replace(".txt", "_count.csv"));
        e.printStackTrace();
        System.exit(0);
      }
    }

    if (countTest == true) {
      if (verbose)
        System.out.println("Counter Test Successfull");
    } else {
      if (verbose)
        System.out.println("Counter Test Failed");
      Autograder.Log("Counter Test Failed");
    }

    return countTest;
  }

  public static boolean TestSet() {
    // Simple Set test
    HashSet<String> hs = new HashSet<String>();
    String[] stuff = { "Comp202", "hello", "Hello", "spring break", "a", "Z" };
    for (String s : stuff) {
      hs.put(s);
    }

    for (String s : stuff) {
      if (!hs.contains(s)) {
        if (verbose)
          System.out.println("The hashset should contain " + s);
        Autograder.Log("The hashset should contain " + s + ". No grade impact but you should debug.");
      }
    }

    if (hs.contains("comp202")) {
      if (verbose)
        System.out.println("The hashset should not contain comp202");
      Autograder.Log("The hashset should not contain comp202. No grade impact but you should debug.");
    }

    if (hs.contains("hellO")) {
      if (verbose)
        System.out.println("The hashset should not contain hellO");
      Autograder.Log("The hashset should not contain hellO. No grade impact but you should debug.");
    }

    if (hs.contains("springbreak")) {
      if (verbose)
        System.out.println("The hashset should not contain hellO");
      Autograder.Log("The hashset should not contain hellO. No grade impact but you should debug.");
    }

    hs.remove("Hello");
    if (verbose)
      Util.print(hs);

    for (String s : stuff) {
      hs.remove(s);
      if (hs.contains(s)) {
        if (verbose)
          System.out.println("The hashset should not contain " + s + " after removal");
        Autograder.Log("The hashset should not contain " + s + " after removal. No grade impact but you should debug.");
      }
    }

    if (verbose)
      Util.print(hs);

    // Test set for files in News folder
    boolean setTest = true;
    String words = "";
    BufferedReader fileReader = null;
    File dir = new File("News");
    File[] files = dir.listFiles();
    String[] fnames = new String[files.length];
    for (int i = 0; i < files.length; i++) {
      fnames[i] = files[i].getName();
      Path path = Paths.get("News/" + fnames[i]);
      try {
        words = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
      } catch (IOException e) {
        System.out.println("Error Reading file: " + path.toString());
        e.printStackTrace();
        System.exit(0);
      }
      String[] record = words.toString().replaceAll("[^A-Za-z0-9� ]+", "").toLowerCase().split("\\s+");
      HashSet<String> hSet = new HashSet<String>();
      for (String s : record) {
        hSet.put(s);
      }
      try {
        fileReader = new BufferedReader(new FileReader("NewsSets/" + fnames[i].replace(".txt", "_set.txt")));
        String line = "";
        while ((line = fileReader.readLine()) != null) {
          if (hSet.contains(line)) {
            hSet.remove(line);
          } else {
            setTest = false;
            if (verbose)
              System.out.println("Set should contain : " + line);
            Autograder.Log("Set should contain : " + line);
            break;
          }
        }
        if (!hSet.isEmpty()) {
          setTest = false;
          if (verbose)
            System.out.println("Set should be empty now.");
          Autograder.Log("Set should be empty now.");
        }

      } catch (IOException e) {
        System.out.println("Error Reading file : " + "NewsSets/" + fnames[i].replace(".txt", "_set.txt"));
        e.printStackTrace();
        System.exit(0);
      }
    }
    if (setTest == true) {
      if (verbose)
        System.out.println("HashSet Test Successfull");
    } else {
      if (verbose)
        System.out.println("HashSet Test Failed");
      Autograder.Log("Counter Test Failed");
    }
    return setTest;
  }

  // optTest can can be 0, 1 or 2
  // 0 - Test all docs in News with given keywords in keywords.txt
  // 1 - Test one doc from News while ignoring a given list of keywords
  // 2 - Test one doc from News while ignoring words less than a specific length
  // optInput can be 0 or 1
  // 0 - file input
  // 1 - given sentences
  public static boolean TestCoOccurrence(Integer optTest, Integer optInput) {

    String contents = "";
    String keywords = "";
    if (optInput == 0) // Input is a file
    {
      if (optTest == 0) {
        contents = loadAllDocs();
        keywords = loadKeyWords();
      } else {
        contents = loadOneDoc();
      }
    } else { // Small input. A few sentences
      if (optTest == 0) {
        contents = "once twice thrice thrice twice thrice. Just an additional dummy sentence.";
        keywords = "once twice thrice";
      } else if (optTest == 1) {
        contents = "Just an additional dummy sentence or a sentence with ignore list and were they not ignored. once twice thrice thrice twice thrice.";
      } else {
        contents = "once twice thrice thrice twice thrice. A sentence with min length words like an be and ch but include are were and was.";
      }
    }

    HashBasedWordCO matrix = new HashBasedWordCO();

    if (optTest == 0) { // Test case for given keywords
      matrix.fillCoMat(contents, keywords.toLowerCase().split("\\s+"));
    } else if (optTest == 1) { // Test case for ignored keywords
      String[] ignore = { "and", "the", "or", "am", "are", "is", "was", "were", "a", "an", "be" };
      matrix.setIgnoredWords(ignore);
      matrix.fillCoMat(contents);

    } else { // Test case for minimum keyword length
      matrix.setMinimumWordLength(3);
      matrix.fillCoMat(contents);
    }

    // Check co-occurance values between two keywords
    // Compare with already computed values in file coMatCombined.csv
    boolean coTest = true;
    BufferedReader fileReader = null;
    try {
      if (optInput == 0) {
        if (optTest == 0)
          fileReader = new BufferedReader(new FileReader("coMatCombined.csv"));
        else if (optTest == 1)
          fileReader = new BufferedReader(new FileReader("coMatIgnore.csv"));
        else
          fileReader = new BufferedReader(new FileReader("coMatMinLen3.csv"));
      } else {
        if (optTest == 0)
          fileReader = new BufferedReader(new FileReader("coMatCombined1.csv"));
        else if (optTest == 1)
          fileReader = new BufferedReader(new FileReader("coMatIgnore1.csv"));
        else
          fileReader = new BufferedReader(new FileReader("coMatMinLen31.csv"));
      }
      String line = "";
      line = fileReader.readLine();
      String[] words = line.split(",");
      int r = 0;
      while ((line = fileReader.readLine()) != null) {
        String[] values = line.split(",");
        for (int i = 0; i < values.length; i++) {
          if (matrix.getCoOccurrenceValue(words[r], words[i]) != Integer.parseInt(values[i])) {
            coTest = false;
            String tmp = "Wrong co-occurance value: "
                + Integer.toString(matrix.getCoOccurrenceValue(words[r], words[i])) + " for " + words[r] + " - "
                + words[i] + ". Should be " + Integer.parseInt(values[i]);
            if (verbose) {
              // Autograder.Log(tmp);
              System.out.println(tmp);
            }
            break;
          }
        }
        r++;
      }
    } catch (IOException e) {
      System.out.println("Error Reading file : coMatCombined.csv");
      e.printStackTrace();
      System.exit(0);
    }

    if (coTest == true) {
      if (verbose)
        System.out
            .println("Co-occurance Test " + optTest.toString() + " with Input " + optInput.toString() + " Successfull");
    } else {
      if (verbose)
        System.out
            .println("Co-occurance Test " + optTest.toString() + " with Input " + optInput.toString() + " Failed");
      Autograder.Log("Co-occurance Test " + optTest.toString() + " with Input " + optInput.toString() + " Failed");
    }

    return coTest;
  }

  public static String loadAllDocs() {
    File dir = new File("News");
    File[] files = dir.listFiles();
    String[] fnames = new String[files.length];
    String allText = "";

    for (int i = 0; i < files.length; i++) {
      fnames[i] = files[i].getName();
      try {
        String contents = new String(Files.readAllBytes(files[i].toPath()), StandardCharsets.UTF_8);
        allText = allText.concat(" ");
        allText = allText.concat(contents);
      } catch (Exception e) {
        System.out.println("IOException at loading file : " + fnames[i]);
        e.printStackTrace();
        System.exit(0);
      }
    }

    return allText;
  }

  public static String loadOneDoc() {
    File dir = new File("News");
    File[] files = dir.listFiles();
    String[] fnames = new String[files.length];
    String allText = "";

    for (int i = 0; i < files.length; i++) {
      if (!files[i].getName().toString().contains("bad06.txt"))
        continue;
      fnames[i] = files[i].getName();
      try {
        String contents = new String(Files.readAllBytes(files[i].toPath()), StandardCharsets.UTF_8);
        allText = allText.concat(" ");
        allText = allText.concat(contents);
      } catch (Exception e) {
        System.out.println("IOException at loading file : " + fnames[i]);
        e.printStackTrace();
        System.exit(0);
      }
    }
    return allText;
  }

  public static String loadKeyWords() {
    File keyfile = new File("keywords.txt");
    String keywords = null;
    try {
      keywords = new String(Files.readAllBytes(keyfile.toPath()), StandardCharsets.UTF_8);
    } catch (Exception e) {
      System.out.println("IOException at loading keywards.txt file.");
      e.printStackTrace();
      System.exit(0);
    }
    return keywords;
  }

  public static void TestCoOccurrenceEZ() {
    String S1 = "once twice thrice thrice twice thrice";
    HashBasedWordCO tmp = new HashBasedWordCO(1);
    tmp.fillCoMat(S1);
    if (verbose) {
      System.out.println("Text: " + S1);
      tmp.printMatrix();
    }

    if (tmp.getCoOccurrenceValue("thrice", "thrice") != 2) {
      if (verbose)
        System.out.println("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("thrice", "thrice"));
      Autograder.Log("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("thrice", "thrice")
          + ". No grade impact but you should debug.");
    }
    if (tmp.getCoOccurrenceValue("thrice", "once") != 0) {
      if (verbose)
        System.out.println("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("thrice", "once"));
      Autograder.Log("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("thrice", "once")
          + ". No grade impact but you should debug.");
    }
    if (tmp.getCoOccurrenceValue("thrice", "twice") != 3) {
      if (verbose)
        System.out.println("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("thrice", "twice"));
      Autograder.Log("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("thrice", "twice")
          + ". No grade impact but you should debug.");
    }
    if (tmp.getCoOccurrenceValue("once", "once") != 0) {
      if (verbose)
        System.out.println("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("once", "once"));
      Autograder.Log("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("once", "once")
          + ". No grade impact but you should debug.");
    }
    if (tmp.getCoOccurrenceValue("once", "twice") != 1) {
      if (verbose)
        System.out.println("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("once", "twice"));
      Autograder.Log("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("once", "twice")
          + ". No grade impact but you should debug.");
    }
    if (tmp.getCoOccurrenceValue("twice", "twice") != 0) {
      if (verbose)
        System.out.println("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("twice", "twice"));
      Autograder.Log("Wrong co-occurance value: " + tmp.getCoOccurrenceValue("twice", "twice")
          + ". No grade impact but you should debug.");
    }
  }

  public static void main(String[] args) {
    Autograder.init();
    // set to false if you do not want any output
    verbose = false;

    if (verbose)
      System.out.println("Testing hashmaps: ");
    TestHashMap hmSC = new TestHashMap(0);
    hmSC.setVerbosity(verbose);
    hmSC.testAll();
    TestHashMap hmDH = new TestHashMap(1);
    hmDH.setVerbosity(verbose);
    hmDH.testAll();

    if (verbose) {
      System.out.println("Testing counter implementations: ");
      System.out.println("Using SC: ");
    }
    if (TestCounter(0) == true)
      Autograder.addGrade(5);
    if (verbose)
      System.out.println("Using DH: ");
    if (TestCounter(1) == true)
      Autograder.addGrade(5);

    if (verbose)
      System.out.println("Testing the set implementation: ");
    if (TestSet() == true)
      Autograder.addGrade(5);

    // FindCommonKeyWordsinFiles(4, 8);
    if (verbose)
      System.out.println("Testing co-occurence: ");

    // The EZ version is not graded but is there to help you debug!
    try {
    TestCoOccurrenceEZ();
    }
    catch (Exception e) { e.printStackTrace(); }

    // Testing all docs with given keywords
    try {
      if (TestCoOccurrence(0, 1) == true)
        Autograder.addGrade(4);
    } catch (Exception e) { e.printStackTrace(); }
    try {
      if (TestCoOccurrence(0, 0) == true)
        Autograder.addGrade(7);
    } catch (Exception e) { e.printStackTrace(); }
    // Testing single doc with skipping keywords in the ignore list (keywords are
    // all unique words in the doc)
    try {
      if (TestCoOccurrence(1, 1) == true)
        Autograder.addGrade(2);
    } catch (Exception e) { e.printStackTrace(); }
    try {
      if (TestCoOccurrence(1, 0) == true)
        Autograder.addGrade(5);
    } catch (Exception e) { e.printStackTrace(); }

    // Testing single doc with skipping keywords of minimum word length (keywords
    // are all unique words in the doc)
    try {
      if (TestCoOccurrence(2, 1) == true)
        Autograder.addGrade(2);
    } catch (Exception e) { e.printStackTrace(); }
    try {
      if (TestCoOccurrence(2, 0) == true)
        Autograder.addGrade(5);
    } catch (Exception e) { e.printStackTrace(); }

    Autograder.printLog();
    Autograder.printGrade();
  }

}
