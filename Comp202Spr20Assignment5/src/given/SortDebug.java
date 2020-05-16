package given;

import code.*;

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

/**
 * The simple version of the sort test, useful for debugging your individual algorithms
 * 
 * @author baakgun
 *
 */
public class SortDebug {
  
  /**
   * Benchmarking function
   * 
   * @param sortAlg: sorting algorithm to benchmark
   * @param array:   data to sort
   * @param output:  whether to print output or not
   * @return
   */
  public static long benchMark(AbstractArraySort<Integer> sortAlg, Integer[] array, boolean output)
  {
    long timeA, timeB;
    if(output)
      System.out.println(sortAlg);
    timeA = System.currentTimeMillis();
    sortAlg.sort(array);
    timeB = System.currentTimeMillis();
    boolean sorted = false;
    if(output)
    {
      sorted = AbstractArraySort.isSorted(array,0,array.length-1);
      System.out.println("Issorted: " + Boolean.toString(sorted));
      System.out.println("Sorting took: " + (timeB - timeA) + " miliseconds");
      System.out.println();
    }
    assert sorted;
    return timeB - timeA;
  }
  
  
  public static void main(String[] args)
  { 
    AbstractArraySort<Integer> debug;   
    //Uncomment the one you are debugging
    InsertionSort<Integer> iSortInt = new InsertionSort<Integer>(); debug = iSortInt;
    //QuickSort<Integer> qSortInt = new QuickSort<Integer>(); debug = qSortInt;
    //MergeSort<Integer> mSortInt = new MergeSort<Integer>(); debug = mSortInt;
    //HeapSort<Integer> hSortInt = new HeapSort<Integer>();   debug = hSortInt;
    //CountingSort<Integer> coSortInt = new CountingSort<Integer>(); debug = coSortInt;
    
    //Number of elements to test. Increase to more than 100 to get non-zero sorting times
    int n = 10000;
    
    Integer[] randIntegers = new Integer[n];
    randIntegers = DataGenerator.generateIntegers(n, "uniform");
    
    if(n < 101)
    {
      System.out.println("Before sorting:");
      AbstractArraySort.printArray(randIntegers);
    }
    
    benchMark(debug, randIntegers, true);
    
    if(n < 101)
    {
      System.out.println("After sorting:");
      AbstractArraySort.printArray(randIntegers);
    }
    debug.printSwaps();
    debug.printCompares();
  }
}
