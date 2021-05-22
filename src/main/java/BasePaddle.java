abstract class BasePaddle implements GameEntity {

  // Must always be odd
  static final int DEFAULT_PADDLE_WIDTH = 5;
  static final int DEFAULT_PADDLE_OFFSET = (DEFAULT_PADDLE_WIDTH - 1) / 2;

  public int paddleCenterX;
  public int paddleY;
  public int width;
  public int paddleOffset;

  BasePaddle(int paddleCenterX, int paddleY) {
    this.paddleCenterX = paddleCenterX;
    this.paddleY = paddleY;
    this.width = DEFAULT_PADDLE_WIDTH;
    this.paddleOffset = DEFAULT_PADDLE_OFFSET;
  }

  BasePaddle(int paddleCenterX, int paddleY, int width) throws IllegalArgumentException {
    this.paddleCenterX = paddleCenterX;
    this.paddleY = paddleY;

    if (width % 2 != 1) {
      throw new IllegalArgumentException("paddle width must always be odd");
    }

    this.width = width;
    this.paddleOffset = (width - 1) / 2;
  }

  @Override
  public char render(int x, int y) {
    if (y == paddleY
        && x >= (paddleCenterX - paddleOffset)
        && x <= (paddleCenterX + paddleOffset)) {
      return getSymbol();
    }
    return Constants.EMPTY_SPACE;
  }

  public int getLeftEdgeXCoord() {
    return paddleCenterX - paddleOffset;
  }

  public int getRightEdgeXCoord() {
    return paddleCenterX + paddleOffset;
  }

  public boolean isBallHittingPaddle(int futureBallX, int futureBallY) {
    return futureBallX >= getLeftEdgeXCoord()
        && futureBallX <= getRightEdgeXCoord()
        && futureBallY == paddleY;
  }

  protected abstract char getSymbol();
}