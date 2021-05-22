class EnemyPaddle extends BasePaddle {

  public static final char DEFAULT_CELL_CHARACTER = '*';

  // The char used to render each cell in the paddle
  private char cellCharacter;

  EnemyPaddle(int paddleCenterX, int paddleY) {
    super(paddleCenterX, paddleY);

    this.cellCharacter = DEFAULT_CELL_CHARACTER;
  }

  EnemyPaddle(int paddleCenterX, int paddleY, char cellCharacter) {
    super(paddleCenterX, paddleY);

    this.cellCharacter = cellCharacter;
  }

  @Override
  protected char getSymbol() {
    return cellCharacter;
  }

  @Override
  public boolean update() {
    return false;
  }
}