import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JRootPane;

public class UserInputListener {

  private int lastInput = -1;

  public UserInputListener() {
    startListeningToUserInput();
  }

  public int getLastInputAndClear() {
    int lastInputReturn = lastInput;
    lastInput = -1;
    return lastInputReturn;
  }

  public void startListeningToUserInput() {
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
}
