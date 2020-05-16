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
 * AUTHORS: Baris Akgun, Ali Tugrul Balci
 *
 * DO NOT MODIFY 
 * 
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DataGenerator {
  
  static Random r = new Random(202);
  
  private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  public static int randomIntRange(int min, int max)
  {
    return r.nextInt((max - min) + 1) + min;
  }
  
  public static double randomDoubleRange(double min, double max)
  {
    return r.nextDouble()*(max-min)+min;
  }
  
  public static String randomString(int minSize, int maxSize) {
    /*minSize = minSize < 1 ? 1 : minSize;
    minSize = minSize > ALPHA_NUMERIC_STRING.length()-1 ? ALPHA_NUMERIC_STRING.length()-1 : minSize;
    
    maxSize = maxSize < 1 ? 1 : maxSize;
    maxSize = maxSize > ALPHA_NUMERIC_STRING.length()-1 ? ALPHA_NUMERIC_STRING.length()-1 : maxSize;*/
    
    
    int size = randomIntRange(minSize, maxSize+1);
    StringBuilder builder = new StringBuilder();

    while (size-- != 0) {
      builder.append(ALPHA_NUMERIC_STRING.charAt(randomIntRange(0, ALPHA_NUMERIC_STRING.length()-1)));
    }

    return builder.toString();
  }
  
  public static Integer[] randomIntRange(int min, int max, int n)
  {
    Integer[] randIntegers = new Integer[n];
    for(int i = 0; i < n; i++)
      randIntegers[i] = randomIntRange(min, max);
    return randIntegers;
  }
  
  public static Double[] randomDoubleRange(double min, double max, int n)
  {
    Double[] randDoubles = new Double[n];
    for(int i = 0; i < n; i++)
      randDoubles[i] = randomDoubleRange(min, max);
    return randDoubles;
  }
  
  public static String[] randomString(int minSize, int maxSize, int n) {
    String[] randomStrings = new String[n];
    for(int i = 0; i < n; i++)
      randomStrings[i] = randomString(minSize, maxSize);
    return randomStrings;
  }
  
  public static Integer[] randomSorted(int min, int max, int n, boolean ascending)
  {
    int step = (max-min)/n;
    Integer[] randIntegers = new Integer[n];
    if(ascending)
    {
      randIntegers[0] = min + randomIntRange(0,step); 
      for(int i = 1; i < n; i++)
        randIntegers[i] = randIntegers[i-1]+randomIntRange(0, step);
    }
    else
    {
      randIntegers[0] = max - randomIntRange(0,step); 
      for(int i = 1; i < n; i++)
        randIntegers[i] = randIntegers[i-1]-randomIntRange(0, step);  
    }
    return randIntegers;
  }
  
  public static Integer[] chunksSorted(int min, int max, int n, int numUnits)//, boolean ascending)
  {
    
    Integer[] randIntegers = new Integer[n];
    int chunkSize = n/numUnits;
    int range = (max - min)/numUnits;
    Integer[] tmp;

    for(int i = 0; i < numUnits; i++)
    {
      tmp = randomIntRange(min + i*range, min+(i+1)*range-1, chunkSize);
      System.arraycopy(tmp, 0, randIntegers, i*chunkSize, chunkSize);
    }
    
    int remaining = n-numUnits*chunkSize;

    if(remaining > 0)
    {
      tmp = randomIntRange(min + numUnits*range, max, remaining);
      System.arraycopy(tmp, 0, randIntegers, numUnits*chunkSize, remaining);
    }
      
    return randIntegers;
  }
  
  public static Integer[] staggered(int min, int max, int n, int numUnits)//, boolean ascending)
  {
    
    Integer[] randIntegers = new Integer[n];
    int staggerSize = n/numUnits;
    int range = (max - min)/numUnits;
    Integer[] tmp;
    System.out.println(staggerSize);
    System.out.println(range);

    for(int i = 0; i < numUnits; i++)
    {
      tmp = randomIntRange(min + i*range, min+(i+1)*range-1, staggerSize);
      System.arraycopy(tmp, 0, randIntegers, i*staggerSize, staggerSize);
    }
    
    int remaining = n-numUnits*staggerSize;
    System.out.println(remaining);
    if(remaining > 0)
    {
      tmp = randomSorted(min, max, remaining, true);//randomIntRange(min + numUnits*range, max, remaining);
      System.arraycopy(tmp, 0, randIntegers, numUnits*staggerSize, remaining);
    }
      
    return randIntegers;
  }

  public static Integer[] read_data(String file_name, int n){
    Integer[] integerArray = new Integer[n];
    try{
      FileReader file = new FileReader(file_name);
      Scanner s = new Scanner(new BufferedReader(file));
      Integer i,j = 0;
      while(s.hasNextInt()){
        if( j == n){
         break;
        }
        i = s.nextInt();
        integerArray[j] = i;
        j++;
      }
      s.close();
    }
    catch(FileNotFoundException ex){
      System.out.println(file_name+" not found.");
    }
    return integerArray;
  }

  public static Integer[] sortedLadder(int min, int max, int n, int numUnits)
  {
    Integer[] randIntegers = new Integer[n];
    
    int ladderSize = n/numUnits;
    int range = (max - min)/numUnits;
    Integer[] tmp = new Integer[ladderSize];

    for(int i = 0; i < numUnits; i++)
    {
      Arrays.fill(tmp, randomIntRange(min + i*range, min+(i+1)*range-1));//randomIntRange(min + i*range, min+(i+1)*range-1, ladderSize);
      System.arraycopy(tmp, 0, randIntegers, i*ladderSize, ladderSize);
    }
    
    int remaining = n-numUnits*ladderSize;

    if(remaining > 0)
    {
      Arrays.fill(tmp, randomIntRange(min+(numUnits)*range, max));//randomIntRange(min + numUnits*range, max, remaining);
      System.arraycopy(tmp, 0, randIntegers, numUnits*ladderSize, remaining);
    }
    
    return randIntegers;
  }

  public static Integer[] rand_data(int n){
    //return read_data(".." + File.separator + "data" + File.separator + "rand.txt", n);
    return read_data("data" + File.separator + "rand.txt", n);
  }
  
  public static Integer[] generateIntegers(int n, String type) {
    Integer[] orig = new Integer[0];
    switch (type)
    {
      case "uniform":
        orig = DataGenerator.randomIntRange(0, 2*n, n);
        break;
      case "constant":
        orig = new Integer[n];
        Arrays.fill(orig, DataGenerator.randomIntRange(0,n));
        break;
      case "sorted":
        orig = DataGenerator.randomSorted(0, n, n, true);
        break;
      case "sortedReverse":
        orig = DataGenerator.randomSorted(0, n, n, false);
        break;
      case "randomizedDuplicates":
        orig = DataGenerator.randomIntRange(0, 63, n);
        break;
      case "staggered":
        orig = DataGenerator.chunksSorted(0, n/2, n, Math.min(n/10, 100));
        break;
      case "sortedChunks":
        orig = DataGenerator.chunksSorted(0, n, n, Math.min(n/10, 100));
        break;
      case "ladder":
        orig = DataGenerator.sortedLadder(0, n, n, Math.min(n/10, 100));
        break;
      case "random":
        orig = DataGenerator.rand_data(n);
        break;
      default:
        orig = new Integer[0];
    }  
    return orig;
  }

}
