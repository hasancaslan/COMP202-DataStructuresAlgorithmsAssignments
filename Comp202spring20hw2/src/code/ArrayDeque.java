package code;

/*
 * ASSIGNMENT 2
 * AUTHOR: HASAN CAN ASLAN
 * Class : ArrayDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the Array Deque yourself
 *
 * MODIFY
 *
 * */

import given.iDeque;

import java.util.Iterator;


/*
 * You must have a circular implementation.
 *
 * WARNING: Modulo operator (%) might create issues with negative numbers.
 * Use Math.floorMod instead if you are having issues
 */
public class ArrayDeque<E> implements iDeque<E> {

    private E[] A; //Do not change this name!
    private int size;
    private int front;
    private int behind;

    public ArrayDeque() {
        this(1000);
    }

    public ArrayDeque(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        A = createNewArrayWithSize(initialCapacity);
        size = 0;
        front = 0;
        behind = 0;
    }

    // This is given to you for your convenience since creating arrays of generics is not straightforward in Java
    @SuppressWarnings({"unchecked"})

    private E[] createNewArrayWithSize(int size) {
        return (E[]) new Object[size];
    }

    public String toString() {
        if (isEmpty())
            return ("");

        StringBuilder sb = new StringBuilder(1000);
        sb.append("[");
        Iterator<E> iter = this.iterator();
        while (iter.hasNext()) {
            E e = iter.next();
            if (e == null)
                continue;
            sb.append(e);
            if (!iter.hasNext())
                sb.append("]");
            else
                sb.append(", ");
        }
        return sb.toString();
    }

    private void resizeStorage() {
        E[] newArray = createNewArrayWithSize(size * 2);
        int i, j;
        for (i = 0; i < A.length - 1; i++) {
            j = (front + i) % A.length;
            newArray[i] = A[j];
        }
        A = newArray;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void addFront(E o) {
        if (size == A.length)
            resizeStorage();

        front = (front + A.length - 1) % A.length;
        A[front] = o;
        size++;
    }

    @Override
    public E removeFront() {
        if (isEmpty())
            return null;

        E tmp = A[front];
        A[front] = null;
        front = (front + 1) % A.length;
        size--;
        return tmp;
    }

    @Override
    public E front() {
        if (isEmpty())
            return null;

        return A[front];
    }

    @Override
    public void addBehind(E o) {
        if (size == A.length)
            resizeStorage();

        A[behind] = o;
        behind = (behind + 1) % A.length;
        size++;
    }

    @Override
    public E removeBehind() {
        if (isEmpty())
            return null;

        behind = (behind + A.length - 1) % A.length;
        E tmp = A[behind];
        A[behind] = null;
        size--;
        return tmp;
    }

    @Override
    public E behind() {
        if (isEmpty())
            return null;

        return A[(behind + A.length - 1) % A.length];
    }

    @Override
    public void clear() {
        A = createNewArrayWithSize(1000);
        size = 0;
        front = 0;
        behind = 0;
    }

    //Must print from front to back
    @Override
    public Iterator<E> iterator() {
        return new ArrayDequeIterator();
    }

    private final class ArrayDequeIterator implements Iterator<E> {
        private int cursor = (front - 1 + A.length) % A.length;

        @Override
        public boolean hasNext() {
            return (cursor + 1) % A.length != behind;
        }

        @Override
        public E next() {
            if (!hasNext())
                return null;

            cursor = (cursor + 1) % A.length;
            return A[cursor];
        }
    }
}
