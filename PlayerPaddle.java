class PlayerPaddle extends BasePaddle {
	
	public static final char DEFAULT_CELL_CHARACTER = '=';

	// The char used to render each cell in the paddle
	private char cellCharacter;

	PlayerPaddle(int paddleCenterX, int paddleY) {
		super(paddleCenterX, paddleY);

		this.cellCharacter = DEFAULT_CELL_CHARACTER;
	}

	PlayerPaddle(int paddleCenterX, int paddleY, char cellCharacter) {
		super(paddleCenterX, paddleY);

		this.cellCharacter = cellCharacter;
	}
}
