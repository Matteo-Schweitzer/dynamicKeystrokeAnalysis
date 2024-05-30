import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyLogger implements KeyListener {
    ArrayList<Long> startTime = new ArrayList<>();
    ArrayList<Long> endTime = new ArrayList<>();
    ArrayList<Long> dwellIntervals = new ArrayList<>();
    ArrayList<Long> flightIntervals = new ArrayList<>();
    Label alert = new Label("");
    private final int BACKSPACE = 8;
    private final int DELETE = 127;
    private final int SHIFT = 16;
    private final int CAPSLOCK = 20;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getExtendedKeyCode();
        if(keyCode != BACKSPACE && keyCode != DELETE && keyCode != SHIFT && keyCode != CAPSLOCK) {
            startTime.add(System.currentTimeMillis());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getExtendedKeyCode();
        if(keyCode != BACKSPACE && keyCode != DELETE && keyCode != SHIFT && keyCode != CAPSLOCK) {
            endTime.add(System.currentTimeMillis());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keyCode = e.getExtendedKeyCode();
        if(keyCode == BACKSPACE || keyCode == DELETE) {
            alert.setText("Please delete all inputs!");
            Database.passwordInput.setText("");
            startTime.clear();
            endTime.clear();
        }
    }
}