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

// The abstract base class for the hashmap. 
// Some of the fields and methods are provided to help you in your implementation

abstract public class AbstractHashMap<Key, Value> implements iMap<Key, Value>, iPrintable<Key> {

  // Current size of the hashmap
  protected int n;

  // Current capacity of the hashmap
  protected int N;
  
  // The parameters for the Multiply-Add-Divide (MAD) hash compression
  protected int a, b;

  // The prime number to be used in MAD
  protected int P;
  
  // Random generator seed specified to be able to get the same values for everyone
  // This will be used to calculate the parameters of the MAD compression
  Random rgen = new Random(202);

  public int size() {
    return n;
  }

  public int capacity() {
    return N;
  }

  public float loadFactor() {
    return (float) n / N;
  }

  public boolean isEmpty() {
    return n == 0;
  }
  
  /*
   * Calculates the hash value of a key given the probe iteration number. 
   * The iteration does not matter for separate chaining! 
   * 
   * Made public to make debugging and testing easier, it should normally be protected or private
   * 
   */
  abstract public int hashValue(Key key, int iter);
  
  /*
   * Do not forget to overwrite this for separate chaining
   * 
   */
  public int hashValue(Key key) {
    return hashValue(key, 0);
  }
  
  /*
   * Returns the smallest prime larger than n
   * 
   */
  protected static int nextPrime(int n) {
    boolean isPrime = false;

    int start = 2;
    n--;
    while (!isPrime) {
      n++;
      int m = (int) Math.ceil(Math.sqrt(n));

      isPrime = true;
      for (int i = start; i <= m; i++) {
        if (n % i == 0) {
          isPrime = false;
          break;
        }
      }
    }
    return n;
  }
  
  /*
   * Assumption: Multiply-Add-Divide (MAD) compression will be used
   * 
   */
  protected void updateHashParams() {
    P = nextPrime(N+1);
    a = rgen.nextInt(N - 1) + 1; // +1 to never have 0!
    b = rgen.nextInt(N);
  }
}
