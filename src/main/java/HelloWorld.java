public class HelloWorld {

  private static final EntityStore entityStore = new EntityStore();
  private static final GameUpdater gameUpdater = new GameUpdater(entityStore);
  private static final Renderer renderer = new Renderer(entityStore);
  private static final LevelCreator levelCreator = new LevelCreator(entityStore);

  public static void main(String[] args) throws InterruptedException {
    levelCreator.createLevel();
    while (true) {
      boolean isGameOver = gameUpdater.update();
      if (!isGameOver) {
        renderer.renderFrame();
      } else {
        break;
      }
    }
    Renderer.clearScreen();
    System.out.println("Game Over");
  }
}