public interface GameEntity {

  /**
   * Updates the current state of the entity. eg. changing position.
   *
   * @return returns true if game is over according to this entity.
   */
  boolean update();

  /**
   * Returns the char that should be rendered in the given x,y location on the screen by this
   * GameEntity. If nothing is to be rendered then return EMPTY_SPACE.
   */
  char render(int x, int y);
}
