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
 * ASSIGNMENT 2
 * AUTHORS: Baris Akgun, Buket Yuksel
 *
 * DO NOT MODIFY 
 * 
 * */

//Deque is a storage-type data structure that can be thought as an extension of stacks and queues
//It supports insertion or deletion of elements from either the front or the back of the data structure
public interface iDeque<E> extends Iterable<E> {
  
  //E is generic. It indicates the type of data to be stored in the deque
	
  //Returns the number of elements stored in the deque
  public int size();
  //Returns true if the deque contains no items; false otherwise
  public boolean isEmpty();

  //Inserts an element at the front of the deque
	public void addFront (E o);
  //Removes and returns the element at the front of the deque. Returns null if the deque is empty.
	public E removeFront();
	//Return the item in the front without removing it. Returns null if the deque is empty.
	public E front();
	
  //Inserts an element at the back of the deque
	public void addBehind(E o);
  //Removes and returns the element at the back of the deque. Returns null if the deque is empty.
	public E removeBehind();
  //Return the item in the back without removing it. Returns null if the deque is empty.
	public E behind();
	
  //Clear all the contents of the deque.
  public void clear();

}
