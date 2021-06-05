public class SomeSingleton {

  private static SomeSingleton s;

  private SomeSingleton(){}

  public static SomeSingleton getInstance() {
    if (s == null) {
      s = new SomeSingleton();
    }
    return s;
  }
}
