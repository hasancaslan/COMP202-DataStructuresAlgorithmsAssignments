package given;

/*
 * Created just to be able to use the same code for printing multiple data structures, nothing of importance
 */
public interface iPrintable<Key> {
  public Iterable<Key> keySet();
  
  public Object get(Key key);
  
  public int size();

}
