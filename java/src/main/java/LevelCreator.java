/**
 * Creates all of the entities in the game and adds them to {@link EntityStore}.
 */
public class LevelCreator {

  private static final int STARTING_BALL_X = 3;
  private static final int STARTING_BALL_Y = 7;

  private static final int STARTING_PADDLE_X = 3;
  private static final int STARTING_PLAYER_PADDLE_Y = Constants.LEVEL_HEIGHT - 2;
  private static final int STARTING_ENEMY_PADDLE_Y = 2;

  private final UserInputListener userInputListener = new UserInputListener();
  private final EntityStore entityStore;

  private Ball ball;
  private final BallPosition ballPosition = new BallPosition() {
    @Override
    public int getBallX() {
      return ball.getBallX();
    }
  };

  public LevelCreator(EntityStore entityStore) {
    this.entityStore = entityStore;
  }

  /**
   * Create all entities for the level and store them into {@link EntityStore}.
   */
  public void createLevel() {
    EnemyPaddle enemyPaddle =
        new EnemyPaddle(STARTING_PADDLE_X, STARTING_ENEMY_PADDLE_Y, ballPosition);
    PlayerPaddle playerPaddle =
        new PlayerPaddle(
            STARTING_PADDLE_X,
            STARTING_PLAYER_PADDLE_Y,
            userInputListener);
    ball = new Ball(STARTING_BALL_X, STARTING_BALL_Y, playerPaddle, enemyPaddle);

    entityStore.add(enemyPaddle);
    entityStore.add(playerPaddle);
    entityStore.add(ball);
    entityStore.add(new Walls());
  }
}
