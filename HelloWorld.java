import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.*;

public class HelloWorld {

	static final int WIDTH = 20;
	static final int HEIGHT = 30;
	// Must alway be odd
	static final int PADDLE_WIDTH = 5;
	static final int PADDLE_OFFSET = (PADDLE_WIDTH - 1) / 2;

	// The position of the ball
	static int ballX = 3;
	static int ballY = 6;

	// The position of the paddle
	static int paddleCenterX = 3;
	static int paddleY = HEIGHT - 2;

	// The velocity of the ball
	static int ballVx = 1;
	static int ballVy = 1;

	static int lastInput = -1;

	public static void listenForKeyboardInput() { 
    	final JFrame frame = new JFrame();  
        frame.setUndecorated(true);  
        frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);  
        
        frame.addKeyListener(new KeyListener() {
            @Override 
            public void keyPressed(KeyEvent e) {  
            	lastInput = e.getKeyCode();
            } 

            @Override 
            public void keyReleased(KeyEvent e) {} 

            @Override 
            public void keyTyped(KeyEvent e) {}  
        });

        frame.setVisible(true);
    }

	private static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  
		
	// returns false if game is over else return true
	private static boolean updateState() {
		// Move paddle
		// 37 left
		// 39 right
		if (lastInput == 37) {
			paddleCenterX -= 1;
			lastInput = -1;
		} else if (lastInput == 39) {
			paddleCenterX += 1;
			lastInput = -1;
		}
		if (paddleCenterX <= 1) {
			paddleCenterX = 2;
		}

		if (paddleCenterX >= WIDTH - 2) {
			paddleCenterX = WIDTH - 3;
		}

		// Update velocity
		if (ballX >= (WIDTH - 2) || ballX <= 1) {
			ballVx = ballVx * -1;
		}

		boolean isBallHittingPaddle = 
			ballX >= paddleCenterX - PADDLE_OFFSET
			&& ballX < paddleCenterX + PADDLE_OFFSET
			&& ballY >= paddleY;

		if (ballY <= 1 || isBallHittingPaddle) {
			ballVy = ballVy * -1;
		}

		// Move the ball
		ballX += ballVx;
		ballY += ballVy;

		if (ballY > paddleY) {
			// Game over
			return false;
		}

		return true;
	}

	private static void renderFrame() {
		for (int y = 0 ;  y < HEIGHT ; y++) {
			StringBuilder stringBuilder = new StringBuilder("");
			for (int x = 0 ; x < WIDTH ; x++) {
				if (ballX == x && ballY == y) {
					stringBuilder.append("o");
				} else if (y == paddleY
					&& x >= (paddleCenterX - PADDLE_OFFSET)
					&& x <= (paddleCenterX + PADDLE_OFFSET)) {
					stringBuilder.append("=");	
				} else if (y == 0 || y == (HEIGHT - 1)) {
					stringBuilder.append("x");
				} else if (x == 0 || x == (WIDTH -1)) {
					stringBuilder.append("x");
				} else {
					stringBuilder.append(" ");
				}
			}

			System.out.println(stringBuilder.toString());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		listenForKeyboardInput();
		while (true) {
			boolean canGameContinue = updateState();
			Thread.sleep(100);
			clearScreen();
			if (canGameContinue) {
				renderFrame();
				System.out.println(lastInput);
			} else {
				break;
			}
		}

		System.out.println("Game Over");
	}
}
