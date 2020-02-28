package code;

/*
 * ASSIGNMENT 2
 * AUTHOR: HASAN CAN ASLAN
 * Class : LLDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the linked list yourself
 * Note that it should be a doubly linked list
 *
 * MODIFY
 *
 * */

import given.iDeque;

import java.util.Iterator;

//If you have been following the class, it should be obvious by now how to implement a Deque wth a doubly linked list
public class LLDeque<E> implements iDeque<E> {
    @SuppressWarnings({"unchecked"})

    //Use sentinel nodes. See slides if needed
    private Node<E> header;
    private Node<E> trailer;
    private int size;

    public LLDeque() {

        //Remember how we initialized the sentinel nodes
        header = new Node<E>(null, null, header);
        trailer = new Node<E>(null, trailer, header);
        header.next = trailer;
        size = 0;
    }

    public String toString() {
        if (isEmpty())
            return "";

        StringBuilder sb = new StringBuilder(size);
        sb.append("[");
        Node<E> tmp = header.next;
        while (tmp.next != trailer) {
            sb.append(tmp.element.toString());
            sb.append(", ");
            tmp = tmp.next;
        }
        sb.append(tmp.element.toString());
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public void addFront(E o) {
        Node<E> first = header.getNext();
        Node<E> newNode = new Node(o, first, header);
        header.setNext(newNode);
        first.setPrev(newNode);
        size++;
    }

    @Override
    public E removeFront() {
        if (size == 0)
            return null;

        Node<E> first = header.getNext();
        E tmp = first.getElement();
        header.setNext(first.getNext());
        first.getNext().setPrev(header);
        size--;
        return tmp;
    }

    @Override
    public E front() {
        if (isEmpty())
            return null;

        return header.getNext().getElement();
    }

    @Override
    public void addBehind(E o) {
        Node<E> last = trailer.getPrev();
        Node<E> newNode = new Node(o, trailer, last);
        trailer.setPrev(newNode);
        last.setNext(newNode);
        size++;
    }

    @Override
    public E removeBehind() {
        if (size == 0)
            return null;

        Node<E> last = trailer.getPrev();
        E tmp = last.getElement();
        trailer.setPrev(last.getPrev());
        last.getPrev().setNext(trailer);
        size--;
        return tmp;
    }

    @Override
    public E behind() {
        if (isEmpty())
            return null;

        return trailer.getPrev().getElement();
    }

    @Override
    public void clear() {
        header.setNext(trailer);
        trailer.setPrev(header);
        size = 0;
    }

    @Override
    public Iterator<E> iterator() { return new LLDequeIterator(); }

    // The nested node class, provided for your convenience. Feel free to modify
    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(T d, Node<T> n, Node<T> p) {
            element = d;
            next = n;
            prev = p;
        }

        public T getElement() { return element; }

        public T setElement(T element) {
            T tmp = this.element;
            this.element = element;
            return tmp;
        }

        public Node<T> getNext() { return next; }

        public void setNext(Node<T> next) { this.next = next; }

        public Node<T> getPrev() { return prev; }

        public void setPrev(Node<T> prev) { this.prev = prev; }
    }

    private final class LLDequeIterator implements Iterator<E> {
        private Node<E> cursor = header;

        @Override
        public boolean hasNext() { return cursor.getNext() != trailer; }

        @Override
        public E next() {
            if(!hasNext())
                return null;

            cursor = cursor.getNext();
            return cursor.getElement();
        }
    }
}
