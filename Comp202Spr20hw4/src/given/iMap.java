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

/*
 * A simple map interface.
 */

public interface iMap<Key, Value> {

  // Returns the number of elements in the map
  public int size();

  // Returns true if the map contains no items; false otherwise
  public boolean isEmpty();

  // If the map has an entry with key k, return its associated value; else, return
  // null
  public Value get(Key k);

  // Insert an item with key k and value v into the map;
  // if key k is not already in the map, then return null;
  // else, return the old value associated with k
  public Value put(Key k, Value v);

  // if the map has an item with key k, remove it from M and
  // return its associated value; else, return null
  public Value remove(Key k);

  // Return an iterable collection (e.g. an ArrayList) of the keys in the map
  public Iterable<Key> keySet();
}
