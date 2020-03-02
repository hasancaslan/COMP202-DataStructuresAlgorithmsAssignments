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

import java.util.Comparator;

/*
 * A simple priority queue interface.
 */

public interface iAdaptablePriorityQueue<Key, Value> {
  
  //Returns the number of elements in the priority queue
  public int size();

  // Returns true if the priority queue contains no items; false otherwise
  public boolean isEmpty();
  
  // Sets the comparator to be used in comparisons
  public void setComparator(Comparator<Key> C);
  
  //Returns the comparator of the priority queue
  public Comparator<Key> getComparator();
  
  // Inserts an entry with key k and value v to the priority queue
  public void insert(Key k, Value v);
  
  // Removes and returns the entry with the smallest key (according to the comparator)
  // or null if the priority queue is empty
  // Decided to return the entry instead of the value in case the key will be needed
  public Entry<Key, Value> pop();
  
  // Returns the entry with the smallest key (according to the comparator)
  // or null if the priority queue is empty
  // Does not remove the entry
  // Decided to return the entry instead of the value in case the key will be needed
  public Entry<Key, Value> top();
  
  // Remove the entry with given key and return its value
  // Return null if the key does not exists
  // As mentioned above this signature is not a realistic for a PQ since keys do not need to be unique!
  public Value remove(Key k);
  
  // Replace the key of the given entry and return the old key
  // Return null if the entry does not exists
  // Make sure to match both the key and the value before replacing anything!
  public Key replaceKey(Entry<Key, Value> entry, Key k);
  
  // Replace the key of the given value and return the old key
  // Return null if the entry with the value does not exists
  // Assume that values are unique! Do not worry about it if not
  public Key replaceKey(Value v, Key k);
  
  // Replace the value of the given entry and return the old value
  // Return null if the entry does not exists
  // Make sure to match both the key and the value before replacing anything!
  public Value replaceValue(Entry<Key, Value> entry, Value v);
}
