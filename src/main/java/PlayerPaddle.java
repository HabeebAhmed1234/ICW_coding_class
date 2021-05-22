class PlayerPaddle extends BasePaddle {

  public static final char DEFAULT_CELL_CHARACTER = '=';

  private final UserInputListener userInputListener;
  // The char used to render each cell in the paddle
  private char cellCharacter;

  PlayerPaddle(
      int paddleCenterX,
      int paddleY,
      UserInputListener userInputListener) {
    super(paddleCenterX, paddleY);

    this.cellCharacter = DEFAULT_CELL_CHARACTER;
    this.userInputListener = userInputListener;
  }

  PlayerPaddle(
      int paddleCenterX,
      int paddleY,
      char cellCharacter,
      UserInputListener userInputListener) {
    super(paddleCenterX, paddleY);

    this.cellCharacter = cellCharacter;
    this.userInputListener = userInputListener;
  }

  @Override
  protected char getSymbol() {
    return cellCharacter;
  }

  @Override
  public boolean update() {
    // Move paddle
    // 37 left
    // 39 right
    int lastInput = userInputListener.getLastInputAndClear();
    if (lastInput == 37) {
      paddleCenterX -= 1;
    } else if (lastInput == 39) {
      paddleCenterX += 1;
    }
    if (paddleCenterX <= 1) {
      paddleCenterX = 2;
    }

    if (paddleCenterX >= Constants.LEVEL_WIDTH - 2) {
      paddleCenterX = Constants.LEVEL_WIDTH - 3;
    }
    return false;
  }
}