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


import java.util.HashSet;
import java.util.Random;

public class DataGenerator {
  
  static Random r = new Random(System.currentTimeMillis());
  
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
  
  public static Integer[] randomIntRangeUnique(int min, int max, int n)
  {
    if(max-min < n)
      return null;
    Integer[] randIntegers = new Integer[n];
    HashSet<Integer> values = new HashSet<Integer>();
    int candidate;
    int i = 0;
    while(true) {
      if(i==n)
        break;
      candidate = randomIntRange(min, max);
      if(values.contains(candidate))
        continue;
      values.add(candidate);
      randIntegers[i] = candidate;
      i++;
    }
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
}
