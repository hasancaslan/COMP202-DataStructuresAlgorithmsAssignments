package autograder;

import java.util.Comparator;

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

import code.ArrayBasedHeap;
import given.Entry;

public class HeapAG<Key, Value> extends ArrayBasedHeap<Key, Value>{
  
  Comparator<Key> comp;
  
  public HeapAG() {
    super();
  }

  public boolean isHeap() {
    comp = getComparator();
    return _isHeap(0, nodes.size());
  }

  private boolean _isHeap(int i, int size) {
    int lc = 2 * i + 1;
    int rc = 2 * i + 2;

    boolean lcExists = lc < size;
    boolean rcExists = rc < size;

    if (!lcExists && !rcExists)
      return true;

    Key parKey = getKey(i);

    boolean retVal = true;
    if (lcExists)
      retVal = retVal && (comp.compare(parKey,getKey(lc)) <= 0) && _isHeap(2 * i + 1, size);
    if (rcExists)
      retVal = retVal && (comp.compare(parKey,getKey(rc)) <= 0) && _isHeap(2 * i + 2, size);
    return retVal;
  }
  
  protected Key getKey(int i) {
    return nodes.get(i).getKey();
  }
  
  public boolean checkIfExists(Entry<Key, Value> entry) {
    for(Entry<Key, Value> item : nodes) {
      if(item.equals(entry))
        return true;
    }
    return false;
  }
  
}
