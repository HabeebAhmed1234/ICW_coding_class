import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JRootPane;

public class HelloWorld {

  // The position of the ball
  static int ballX = 3;
  static int ballY = 6;

  // The velocity of the ball
  static int ballVx = 1;
  static int ballVy = 1;

  // Objects
  private static PlayerPaddle playerPaddle = new PlayerPaddle(3, HEIGHT - 2);

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
    } else if (lastInput == 39) {
      playerPaddle.paddleCenterX += 1;
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

    boolean isBallHittingPaddle = playerPaddle.isBallHittingPaddle(ballX, ballY);

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