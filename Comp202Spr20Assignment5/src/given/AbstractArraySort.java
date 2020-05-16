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

/**
 * Abstract class for sorting arrays. It defines several useful methods. 
 * You must override the sort method to implement a concrete class.
 * 
 * Do not include any other class (e.g. benchmarkLog) in your own class! 
 *
 * @author baakgun
 * 
 * @param <K>
 */
public abstract class AbstractArraySort<K extends Comparable<K>> {
  
  protected String name;
  protected int number_of_swaps;
  protected int number_of_compares;
  
  /**
   * Override!
   * @param inputArray
   */
  public abstract void sort(K[] inputArray);
  
  /**
   * swap function for arrays
   * 
   * @param arr:  Array
   * @param pos1: first element to be swapped
   * @param pos2: second element to be swapped
   */
  protected void swap(K[] arr, int pos1, int pos2){
    number_of_swaps++;
    K temp = arr[pos1];
    arr[pos1] = arr[pos2];
    arr[pos2] = temp;
  }
  
  /**
  *compare function for two objects
  * 
  * @param obj1: first object to be compared
  * @param obj2: second object to be compared
  * @return 0 if obj1 = obj2, positive if obj1 > obj2, negative if obj1 < obj2
  */
  protected int compare(K obj1, K obj2){
    number_of_compares++;
    return obj1.compareTo(obj2);
  }

  /**
   * A function to check whether the given array is sorted between lo and hi
   * 
   * @param array
   * @param lo
   * @param hi
   * @return true if sorted, false otherwise
   */
  public static <K extends Comparable<K>> boolean isSorted(K[] array, int lo, int hi)
  {
    for(int i = lo; i < hi; i++)
    {
      if(array[i].compareTo(array[i+1]) > 0)
      {
        System.out.println(array[i] + " " + array[i+1] + " " + Integer.toString(i));
        return false;
      }
    }
    return true;
  }
  
  /**
   * Prints the input array. 
   * Useful in debugging.
   * 
   * @param array
   */
  
  public static <K> void printArray(K[] array)
  {
    System.out.print("[");
    for(int i = 0; i < array.length-1; i++)
      System.out.print(array[i].toString() + ", ");
    System.out.print(array[array.length-1].toString());
    System.out.println("]");
  }
  
  /**
   * Prints the input array between lo and hi (both inclusive)
   * Useful in debugging.
   * @param array
   * @param lo
   * @param hi
   */
  public static <K> void printArray(K[] array, int lo, int hi)
  {
    System.out.print("[");
    for(int i = lo; i < hi; i++)
      System.out.print(array[i].toString() + ", ");
    System.out.print(array[hi].toString());
    System.out.println("]");
  }

  public void initTest(){
    number_of_swaps = 0;
    number_of_compares = 0;
  }

  public void printSwaps(){
    System.out.println(name+" swaped elements of the array " + number_of_swaps + " time(s).");
  }

  public void printCompares(){
    System.out.println(name+" compared elements of the array " + number_of_compares + " time(s).");
  }

  public Integer getSwaps(){
    return number_of_swaps;
  }

  public Integer getCompares(){
    return number_of_compares;
  }
  
  @Override
  public String toString()
  {
    return name;
  }

}
