public class HelloWorld {

	static final int WIDTH = 30;
	static final int HEIGHT = 50;

	// The position of the ball
	static int ballX = 3;
	static int ballY = 6;

	// The velocity of the ball
	static int ballVx = 1;
	static int ballVy = 1;

	private static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  

	// returns false if game is over else return true
	private static boolean updateState() {
		ballX += ballVx;
		ballY += ballVy;

		if (ballX >= (WIDTH - 2) || ballX <= 1) {
			ballVx = ballVx * -1;
		}

		if (ballY <= 1) {
			ballVy = ballVy * -1;
		}

		if (ballY > HEIGHT) {
			// Game over
			return false;
		}

		return true;
	}

	private static void renderFrame() {
		for (int y = 0 ;  y < HEIGHT ; y++) {
			StringBuilder stringBuilder = new StringBuilder("");
			for (int x = 0 ; x < WIDTH ; x++) {
				if (y == 0 || y == (HEIGHT - 1)) {
					stringBuilder.append("x");
				} else if (x == 0 || x == (WIDTH -1)) {
					stringBuilder.append("x");
				} else if (ballX == x && ballY == y) {
					stringBuilder.append("o");
				} else {
					stringBuilder.append(" ");
				}
			}

			System.out.println(stringBuilder.toString());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		while (true) {
			boolean canGameContinue = updateState();
			Thread.sleep(100);
			clearScreen();
			if (canGameContinue) {
				renderFrame();
			} else {
				break;
			}
		}

		System.out.println("Game Over");
	}
}
