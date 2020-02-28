package code;

import given.Util;
import given.iDeque;

/* 
 * ASSIGNMENT 2
 * AUTHOR: HASAN CAN ASLAN
 * Class : Queue
 *
 * MODIFY 
 * 
 * */

import given.iSimpleContainer;

//Queue implementation
//E is the element type
//C is the underlying Deque type
public class Queue<C extends iDeque<E>, E> implements iSimpleContainer <E> {
  
  //C is generic. It indicates the type of the deque to store the elements
  //E is generic. It indicates the type of data to be stored in the deque

  C deque;
  
  public Queue(C inDeque){ deque = inDeque; }
  
  public String toString() {
    return deque.toString();
  }
  
  @Override
  public void push(E obj) { deque.addBehind(obj); }

  @Override
  public E pop() { return deque.removeFront(); }

  @Override
  public E peek() { return deque.front(); }

  @Override
  public int size() { return deque.size(); }

  @Override
  public boolean isEmpty() { return deque.isEmpty(); }

  @Override
  public void clear() { deque.clear(); }
}
