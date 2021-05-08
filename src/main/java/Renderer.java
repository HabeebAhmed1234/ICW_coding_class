/**
 * Renders a frame by using the render functions of every entity in the game.
 */
public class Renderer {

  private EntityStore entityStore;

  public Renderer(EntityStore entityStore) {
    this.entityStore = entityStore;
  }

  public void renderFrame() {
    for (int y = 0; y < Constants.LEVEL_HEIGHT; y++) {
      StringBuilder stringBuilder = new StringBuilder("");
      for (int x = 0; x < Constants.LEVEL_WIDTH; x++) {
        stringBuilder.append(getCellValue(x, y));
      }
      System.out.println(stringBuilder.toString());
    }
  }

  private char getCellValue(int x, int y) {
    for (int i = 0 ; i < entityStore.getSize() ; i++) {
      GameEntity entity = entityStore.get(i);
      char cellValue = entity.render(x, y);
      if (cellValue != Constants.EMPTY_SPACE)  {
        return cellValue;
      }
    }
    return ' ';
  }
}

/*
if (ballX == x && ballY == y) {
  stringBuilder.append("o");
} else if (y == playerPaddle.paddleY
    && x >= (playerPaddle.paddleCenterX - BasePaddle.DEFAULT_PADDLE_OFFSET)
    && x <= (playerPaddle.paddleCenterX + BasePaddle.DEFAULT_PADDLE_OFFSET)) {
  stringBuilder.append("=");
} else if (y == 0 || y == (Constants.LEVEL_HEIGHT - 1)) {
  stringBuilder.append("x");
} else if (x == 0 || x == (Constants.LEVEL_WIDTH - 1)) {
  stringBuilder.append("x");
} else {
  stringBuilder.append(" ");
}
 */
