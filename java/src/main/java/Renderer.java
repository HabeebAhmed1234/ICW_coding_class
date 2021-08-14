/**
 * Renders a frame by using the render functions of every entity in the game.
 */
public class Renderer {

  private EntityStore entityStore;

  public Renderer(EntityStore entityStore) {
    this.entityStore = entityStore;
  }

  public void renderFrame() throws InterruptedException {
    Thread.sleep(100);
    clearScreen();
    for (int y = 0; y < Constants.LEVEL_HEIGHT; y++) {
      StringBuilder stringBuilder = new StringBuilder("");
      for (int x = 0; x < Constants.LEVEL_WIDTH; x++) {
        stringBuilder.append(getCellValue(x, y));
      }
      System.out.println(stringBuilder.toString());
    }
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private char getCellValue(int x, int y) {
    for (int i = 0; i < entityStore.getSize(); i++) {
      GameEntity entity = entityStore.get(i);
      char cellValue = entity.render(x, y);
      if (cellValue != Constants.EMPTY_SPACE) {
        return cellValue;
      }
    }
    return ' ';
  }
}
