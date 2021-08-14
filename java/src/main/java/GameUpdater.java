public class GameUpdater {

  private EntityStore entityStore;

  public GameUpdater(EntityStore entityStore) {
    this.entityStore = entityStore;
  }

  /** Returns true if the game is over. */
  public boolean update() {
    for (int i = 0; i < entityStore.getSize(); i++) {
      if (entityStore.get(i).update()) {
        return true;
      }
    }
    return false;
  }
}
