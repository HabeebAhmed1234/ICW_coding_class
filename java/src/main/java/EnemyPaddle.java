public class EnemyPaddle extends BasePaddle {

  public static final char DEFAULT_CELL_CHARACTER = '*';

  private final BallPosition ballPosition;
  // The char used to render each cell in the paddle
  private char cellCharacter;

  EnemyPaddle(int paddleCenterX, int paddleY, BallPosition ballPosition) {
    super(paddleCenterX, paddleY);
    this.ballPosition = ballPosition;
    this.cellCharacter = DEFAULT_CELL_CHARACTER;
  }

  EnemyPaddle(int paddleCenterX, int paddleY, char cellCharacter, BallPosition ballPosition) {
    super(paddleCenterX, paddleY);

    this.ballPosition = ballPosition;
    this.cellCharacter = cellCharacter;
  }

  @Override
  protected char getSymbol() {
    return cellCharacter;
  }

  @Override
  public boolean update() {
    paddleCenterX = ballPosition.getBallX();
    return false;
  }
}