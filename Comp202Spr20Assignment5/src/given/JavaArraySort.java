package given;

import java.util.Arrays;

/**
 * A class that wraps the default array sort function of java to be used in
 * comparisons.
 * 
 * @author baakgun
 *
 * @param <K>
 */
public class JavaArraySort<K extends Comparable<K>> extends AbstractArraySort<K> {

  JavaArraySort() {
    this.name = "Java Default Sort";
  }

  @Override
  public void sort(K[] inputArray) {
    Arrays.sort(inputArray);
  }

}
