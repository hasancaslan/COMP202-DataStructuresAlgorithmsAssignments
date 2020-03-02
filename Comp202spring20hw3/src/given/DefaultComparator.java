package given;

import java.util.Comparator;

public class DefaultComparator<A extends Comparable<A>>  implements Comparator<A> {
  
  boolean isReversed;
  
  public DefaultComparator() {
    this(false);
  }
  
  public DefaultComparator(boolean reversed) {
    isReversed = reversed;
  }
  
  public int compare(A obj1, A obj2) {
    if (obj1 == null) {
        return -1;
    }
    if (obj2 == null) {
        return 1;
    }
    if (obj1.equals( obj2 )) {
        return 0;
    }
    if(isReversed)
      return -obj1.compareTo(obj2);
    else
      return obj1.compareTo(obj2);
  }
}
