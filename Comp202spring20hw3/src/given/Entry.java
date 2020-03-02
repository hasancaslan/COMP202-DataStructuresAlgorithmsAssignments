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

import java.util.Objects;

public class Entry<Key, Value> {
  protected Key k;
  protected Value v;
  
  public Entry(Key key, Value value)
  {
    k = key;
    v = value;
  }
  
  public Entry()
  {
    k = null;
    v = null;
  }
  
  public Key getKey() {
    return k;
  }
  
  public void setKey(Key newK) {
    k = newK;
  }
  
  public Value getValue() {
    return v;
  }
  
  public void setValue(Value newV) {
    v = newV;
  }
  
  @Override
  public String toString() {
    return "(Key: " + k.toString() +")";// + ", Value: " + v.toString() + ")";  
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) 
      return false;
    if(obj == this)
       return true;   
    if (!Entry.class.isAssignableFrom(obj.getClass())) 
      return false;
    @SuppressWarnings("unchecked")
    final Entry<Key, Value> other = (Entry<Key, Value>) obj;
    return (this.k.equals(other.k) && this.v.equals(other.v));
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(k, v);
  }
}
