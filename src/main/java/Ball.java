public class Ball implements GameEntity {

  private static final char BALL_SYMBOL = 'o';

  private int ballX;
  private int ballY;

  public Ball(int startX, int startY) {
    this.ballX = startX;
    this.ballY = startY;
  }

  @Override
  public void update() {

  }

  @Override
  public char render(int x, int y) {
    if (x == ballX && y == ballY) {
      return BALL_SYMBOL;
    }
    return Constants.EMPTY_SPACE;
  }
}
