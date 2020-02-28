package code;

import given.iDeque;
import given.iSimpleContainer;

/*
 * ASSIGNMENT 2
 * AUTHOR:  <Insert Student Name>
 * Class : Stack
 *
 * MODIFY
 *
 * */


//Stack Implementation
//E is the element type
//C is the underlying Deque type
public class Stack<C extends iDeque<E>, E> implements iSimpleContainer<E> {

//C is generic. It indicates the type of the deque to store the elements
    //E is generic. It indicates the type of data to be stored in the deque

    C deque;

    public Stack(C inDeque) { deque = inDeque; }

    public String toString() { return deque.toString(); }

    @Override
    public void push(E obj) { deque.addBehind(obj); }

    @Override
    public E pop() {
        if (isEmpty())
            return null;

        return deque.removeBehind();
    }

    @Override
    public E peek() {
        if (isEmpty())
            return null;

        return deque.behind();
    }

    @Override
    public int size() { return deque.size(); }

    @Override
    public boolean isEmpty() { return deque.isEmpty(); }

    @Override
    public void clear() { deque.clear(); }
}
