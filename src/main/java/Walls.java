public class Walls implements GameEntity {

  private static final char WALLS_SYMBOL = 'x';

  @Override
  public boolean update() {
    /* NOOP */
    return false;
  }

  @Override
  public char render(int x, int y) {
    if (x == 0 || x == (Constants.LEVEL_WIDTH - 1)) {
      return WALLS_SYMBOL;
    }
    return Constants.EMPTY_SPACE;
  }
}
