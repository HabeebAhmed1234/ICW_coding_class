public interface GameEntity {

  void update();

  /**
   * Returns the char that should be rendered in the given x,y
   * location on the screen by this GameEntity. If nothing is to be
   * rendered then return EMPTY_SPACE.
   */
  char render(int x, int y);
}
