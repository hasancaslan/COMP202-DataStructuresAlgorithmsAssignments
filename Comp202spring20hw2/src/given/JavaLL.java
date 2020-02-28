package given;

import java.util.LinkedList;

public class JavaLL<E> extends LinkedList<E> implements iDeque<E> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  @Override
  public void addFront(E o) {
    super.addFirst(o);
  }

  @Override
  public E removeFront() {
    if (isEmpty())
      return null;
    return super.removeFirst();
  }

  @Override
  public E front() {
    if (isEmpty())
      return null;
    return super.getFirst();
  }

  @Override
  public void addBehind(E o) {
    super.addLast(o);
  }

  @Override
  public E removeBehind() {
    if (isEmpty())
      return null;
    return super.removeLast();
  }

  @Override
  public E behind() {
    if (isEmpty())
      return null;
    return super.getLast();
  }  
}
