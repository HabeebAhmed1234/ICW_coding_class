public class Ball implements GameEntity {

  private static final char BALL_SYMBOL = 'o';

  // The position of the ball
  private int ballX;
  private int ballY;

  // The velocity of the ball
  static int ballVx = 1;
  static int ballVy = 1;

  // References to paddles
  private final PlayerPaddle playerPaddle;
  private final EnemyPaddle enemyPaddle;

  public Ball(int startX, int startY, PlayerPaddle playerPaddle, EnemyPaddle enemyPaddle) {
    this.ballX = startX;
    this.ballY = startY;
    this.playerPaddle = playerPaddle;
    this.enemyPaddle = enemyPaddle;
  }

  public int getBallX() {
    return ballX;
  }

  @Override
  public boolean update() {
    // Update velocity
    if (ballX >= (Constants.LEVEL_WIDTH - 2) || ballX <= 1) {
      ballVx = ballVx * -1;
    }

    int futureBallX = ballX + ballVx;
    int futureBallY = ballY + ballVy;

    if (willBallHitPaddles(futureBallX, futureBallY)) {
      ballVy = ballVy * -1;
    }

    // Move the ball
    ballX += ballVx;
    ballY += ballVy;

    if (ballY > Constants.LEVEL_HEIGHT || ballY < 0) {
      // Game over
      return true;
    }

    return false;
  }

  @Override
  public char render(int x, int y) {
    if (x == ballX && y == ballY) {
      return BALL_SYMBOL;
    }
    return Constants.EMPTY_SPACE;
  }

  private boolean willBallHitPaddles(int futureBallx, int futureBallY) {
    return playerPaddle.isBallHittingPaddle(futureBallx, futureBallY) ||
        enemyPaddle.isBallHittingPaddle(futureBallx, futureBallY);
  }
}
