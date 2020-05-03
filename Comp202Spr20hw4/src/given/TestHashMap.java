package given;

/*
 * Copyright 2018 Muhammad Nufail Farroqi
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
 * AUTHORS: Muhammad Nufail Farroqi, Baris Akgun
 *
 * DO NOT MODIFY 
 * 
 * */

import java.util.*;

import autograder.Autograder;
import code.*;

public class TestHashMap {

  private AbstractHashMap<String, String> mapOne;
  private AbstractHashMap<String, String> mapThree;
  private AbstractHashMap<String, String> mapFive;

  private int mapType; // 0 = SC, 1 = DH

  private boolean verbose;

  public TestHashMap() {
    mapType = 0;
    mapOne = new HashMapSC<String, String>();
    mapThree = new HashMapSC<String, String>();
    mapFive = new HashMapSC<String, String>();
    verbose = true;
  }

  public TestHashMap(int mapT) {
    mapType = mapT;
    if (mapType == 0) {
      mapOne = new HashMapSC<String, String>();
      mapThree = new HashMapSC<String, String>();
      mapFive = new HashMapSC<String, String>();
    } else {
      mapType = 1;
      mapOne = new HashMapDH<String, String>();
      mapThree = new HashMapDH<String, String>();
      mapFive = new HashMapDH<String, String>();
    }
    verbose = true;
  }

  public void setVerbosity(boolean v) {
    verbose = v;
  }

  public boolean testPut() {
    try {
      mapOne.put("Rene", "CEO");

      mapThree.put("Rene", "CEO");
      mapThree.put("Ronny", "CTO");
      mapThree.put("Simone", "QAM");

      mapFive.put("Rene", "CEO");
      mapFive.put("Ronny", "CTO");
      mapFive.put("Simone", "QAM");
      mapFive.put("Michael", "Developer001");
      mapFive.put("Jens", "Developer002");
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public boolean testPutOverwite() {
    int oldSize = mapFive.size();
    mapFive.put("Jens", "Developer000");
    int newSize = mapFive.size();

    if (mapFive.get("Jens") == "Developer000" && oldSize == newSize)
      return true;

    return false;
  }

  public boolean testPutNull() {
    try {
      if (mapOne.put(null, "CEO") == null)
        return true;
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  public boolean testGet() {
    if (mapFive.get("Simone") == "QAM")
      return true;
    return false;
  }

  public boolean testGetMissingKey() {
    try {
      if (mapFive.get("Missing") == null)
        return true;
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  public boolean testGetNull() {
    try {
      if (mapThree.get(null) == null)
        return true;
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  public boolean testRemove() {
    int oldSize = mapThree.size();
    String rv = mapThree.remove("Ronny");
    int newSize = mapThree.size();
    if (rv == "CTO" && mapThree.get("Ronny") == null && newSize == oldSize - 1)
      return true;

    return false;
  }

  public boolean testRemoveMissing() {
    int oldSize = mapThree.size();
    try {
      if (mapThree.remove("Missing") == null)
        if (mapThree.size() == oldSize)
          return true;
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  public boolean testRemoveNull() {
    int oldSize = mapThree.size();
    try {
      if (mapThree.remove(null) == null)
        if (mapThree.size() == oldSize)
          return true;
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  public boolean testSize() {

    if (mapOne.size() == 1 && mapThree.size() == 3 && mapFive.size() == 5)
      return true;

    return false;
  }

  public boolean testHashValue() {
    if (mapType == 0) {
      if (mapFive.hashValue("Hash1", 0) == 8 && mapFive.hashValue("Hash1", 1) == 8 && mapFive.hashValue("Hash1", 2) == 8
          && mapFive.hashValue("TestHashKey", 0) == 46 && mapFive.hashValue("DifferentKey", 0) == 1)
        return true;
    } else {
      if (mapFive.hashValue("Hash1", 0) == 8 && mapFive.hashValue("Hash1", 1) == 26
          && mapFive.hashValue("Hash1", 2) == 44 && mapFive.hashValue("TestHashKey", 0) == 46
          && mapFive.hashValue("DifferentKey", 0) == 1)
        return true;
    }

    return false;
  }

  public boolean testRemoveAll() {
    Set<String> keys = (Set<String>) mapFive.keySet();
    List<String> tempKeys = new ArrayList<String>();

    for (String key : keys)
      tempKeys.add(key);
    keys.clear();

    for (String key : tempKeys)
      mapFive.remove(key);
    tempKeys.clear();

    if (mapFive.size() == 0)
      return true;

    return false;
  }

  public void testAll() {
    // int grade = 0;
    String hmType;
    if (mapType == 0) {
      if (verbose)
        System.out.println("Testing SC: ");
      hmType = "SC";
    } else {
      if (verbose)
        System.out.println("Testing DH: ");
      hmType = "DH";
    }

    boolean putting = false;
    boolean getting = false;
    boolean removing = false;

    if (testPut() == true) {
      if (verbose)
        System.out.println("\t put method test no exception");
      // Autograder.addGrade(3);
      putting = true;
    } else {
      if (verbose)
        System.out.println("\t put method test Failed");
      Autograder.Log(hmType + " put method test Failed");
    }

    if (testGet() == true) {
      if (verbose)
        System.out.println("\t get method test Successfull");
      Autograder.addGrade(3);
      getting = true;
    } else {
      if (verbose)
        System.out.println("\t get method test Failed");
      Autograder.Log(hmType + " get method test Failed");
      putting = false;
    }

    if (testPutOverwite() == true) {
      if (verbose)
        System.out.println("\t put (overwite value) method test Successfull");
      if (putting)
        Autograder.addGrade(3);
      else
        Autograder.Log(hmType + " put method test Failed");
      Autograder.addGrade(3);
    } else {
      if (verbose)
        System.out.println("\t put (overwite value) method test Failed");
      Autograder.Log(hmType + "  put (overwite value) method test Failed");
    }
    if (testPutNull() == true) {
      if (verbose)
        System.out.println("\t put (null key) method test no exception");
      if (putting)
        Autograder.addGrade(1);
    } else {
      if (verbose)
        System.out.println("\t put (null key) method test Failed");
      Autograder.Log(hmType + " put (null key) method test Failed");
    }

    if (testGetMissingKey() == true) {
      if (verbose)
        System.out.println("\t get (missing key) method test no exception");
      if (getting)
        Autograder.addGrade(3);
    } else {
      if (verbose)
        System.out.println("\t get (missing key) method test Failed");
      Autograder.Log(hmType + " get (missing key) method test Failed");
    }
    if (testGetNull() == true) {
      if (verbose)
        System.out.println("\t get (null key) method test no exception");
      if (getting)
        Autograder.addGrade(1);
    } else {
      if (verbose)
        System.out.println("\t get (null key) method test Failed");
      Autograder.Log(hmType + " get (null key) method test Failed");
    }

    if (testSize() == true) {
      if (verbose)
        System.out.println("\t size method test Successfull");
      Autograder.addGrade(3);
    } else {
      if (verbose)
        System.out.println("\t size method test Failed");
      Autograder.Log(hmType + " size method test Failed");
    }

    if (testRemove() == true) {
      if (verbose)
        System.out.println("\t remove method test Successfull");
      removing = true;
      Autograder.addGrade(3);
    } else {
      if (verbose)
        System.out.println("\t remove method test Failed");
      Autograder.Log(hmType + " remove method test Failed");
    }
    if (testRemoveMissing() == true) {
      if (verbose)
        System.out.println("\t remove (missing key) method test no exception");
      if (removing)
        Autograder.addGrade(3);
    } else {
      if (verbose)
        System.out.println("\t remove (missing key) method test Failed");
      Autograder.Log(hmType + " remove (missing key) method test Failed");
    }
    if (testRemoveNull() == true) {
      if (verbose)
        System.out.println("\t remove (null key) method test no exception");
      if (removing)
        Autograder.addGrade(1);
    } else {
      if (verbose)
        System.out.println("\t remove (null key) method test Failed");
      Autograder.Log(hmType + " remove (null key) method test Failed");
    }

    try {
      if (testRemoveAll() == true) {
        if (verbose)
          System.out.println("\t remove all elements using keySet method test Successfull");
        Autograder.addGrade(3);
      } else {
        if (verbose)
          System.out.println("\t remove all elements using keySet method test Failed");
        Autograder.Log(hmType + " remove all elements using keySet method test Failed");
      }
    } catch (Exception e) {
      Autograder.Log(hmType + " remove all elements using keySet resulted in an exception");
    }

    if (testHashValue() == true) {
      if (verbose)
        System.out.println("\t hashValue method test Successfull");
      Autograder.addGrade(3);
    } else {
      if (verbose)
        System.out.println("\t hashValue method test Failed");
      Autograder.Log(hmType + " hashValue method test Failed");
    }
  }

}