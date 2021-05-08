import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JRootPane;

public class HelloWorld {

  static final int WIDTH = 20;
  static final int HEIGHT = 30;

  // The position of the ball
  static int ballX = 3;
  static int ballY = 6;

  // The velocity of the ball
  static int ballVx = 1;
  static int ballVy = 1;

  static int lastInput = -1;

  // Objects
  private static PlayerPaddle playerPaddle = new PlayerPaddle(3, HEIGHT - 2);

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
      public void keyReleased(KeyEvent e) {
      }

      @Override
      public void keyTyped(KeyEvent e) {
      }
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
      playerPaddle.paddleCenterX -= 1;
      lastInput = -1;
    } else if (lastInput == 39) {
      playerPaddle.paddleCenterX += 1;
      lastInput = -1;
    }
    if (playerPaddle.paddleCenterX <= 1) {
      playerPaddle.paddleCenterX = 2;
    }

    if (playerPaddle.paddleCenterX >= WIDTH - 2) {
      playerPaddle.paddleCenterX = WIDTH - 3;
    }

    // Update velocity
    if (ballX >= (WIDTH - 2) || ballX <= 1) {
      ballVx = ballVx * -1;
    }

    boolean isBallHittingPaddle =
        ballX >= playerPaddle.getLeftEdgeXCoord()
            && ballX <= playerPaddle.getRightEdgeXCoord()
            && ballY >= playerPaddle.paddleY;

    if (ballY <= 1 || isBallHittingPaddle) {
      ballVy = ballVy * -1;
    }

    // Move the ball
    ballX += ballVx;
    ballY += ballVy;

    if (ballY > playerPaddle.paddleY) {
      // Game over
      return false;
    }

    return true;
  }

  private static void renderFrame() {
    for (int y = 0; y < HEIGHT; y++) {
      StringBuilder stringBuilder = new StringBuilder("");
      for (int x = 0; x < WIDTH; x++) {
        if (ballX == x && ballY == y) {
          stringBuilder.append("o");
        } else if (y == playerPaddle.paddleY
            && x >= (playerPaddle.paddleCenterX - BasePaddle.DEFAULT_PADDLE_OFFSET)
            && x <= (playerPaddle.paddleCenterX + BasePaddle.DEFAULT_PADDLE_OFFSET)) {
          stringBuilder.append("=");
        } else if (y == 0 || y == (HEIGHT - 1)) {
          stringBuilder.append("x");
        } else if (x == 0 || x == (WIDTH - 1)) {
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