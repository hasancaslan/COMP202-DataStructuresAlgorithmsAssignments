package given;

import java.util.Random;

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

public class Util {
  public static void NotImplementedYet()
  {
    String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
      String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
      String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
      int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        
      String message = "Not implemented yet:" + System.lineSeparator() + className + "." + methodName + "():" + lineNumber;
        
      throw new UnsupportedOperationException(message);
  }
  
  public static void NotImplementedYetSoft()
  {
    String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
      String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
      String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
      int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        
      String message = "Not implemented yet:" + System.lineSeparator() + className + "." + methodName + "():" + lineNumber;
        
      System.out.println(message);
  }
  
  public static class myVector {
    
    float [] holder;
    int size;
    
    static Random r = new Random(System.currentTimeMillis());
    
    public myVector(int size) {
      holder = new float[size];
      if(size < 1)
        throw new IllegalArgumentException("Size must be a positive integer");
      this.size = size;
    }
    
    public float sum() {
      float sum = 0;
      for(int i = 0; i < size; i++) {
        sum += holder[i];  
      }
      
      return sum;
    }
    
    public float[] cumsum() {
      float[] csum = new float[size];
      csum[0] = holder[0];
      for(int i = 1; i < size; i++) {
        csum[i] = csum[i-1]+holder[i];  
      }
      
      return csum;
    }
    
    public void normalize() {
      float sum = sum();
      for(int i = 0; i < size; i++) {
        holder[i] /= sum;   
      }
    }
    
    private void checkIndex(int i) {
      if(i > size || i < 0)
        throw new ArrayIndexOutOfBoundsException("Trying to access " + i + " in a vector of size " + size);
    }
    
    public void set(int i, float x) {
      checkIndex(i);
      holder[i] = x;
    }
    
    public float get(int i) {
      checkIndex(i);
      return holder[i];
    }
    
    
    public int sampleIndex() {
      float[] csum = cumsum();
      float f = r.nextFloat()*csum[size-1];
      int i;
      for(i = 0; i < size-1; i++) {
        if(csum[i] >= f)
          break;
      }
      return i;
    }
    
    public void setSeed(long seed) {
      r.setSeed(seed); 
    }
  }
  
  

}
