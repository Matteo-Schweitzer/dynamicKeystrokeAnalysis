import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyLogger implements KeyListener {
    ArrayList<Long> startTime = new ArrayList<>();
    ArrayList<Long> endTime = new ArrayList<>();
    ArrayList<Long> intervals = new ArrayList<>();
    ArrayList<Long> dwellIntervals = new ArrayList<>();
    Label alert = new Label("");

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getExtendedKeyCode();
        if(keyCode != 8 && keyCode != 127 && keyCode != 16) {
            startTime.add(System.currentTimeMillis());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getExtendedKeyCode();
        if(keyCode != 8 && keyCode != 127 && keyCode != 16) {
            endTime.add(System.currentTimeMillis());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keyCode = e.getExtendedKeyCode();
        if(keyCode == 8 || keyCode == 127) {
            alert.setText("Please delete all inputs!");
            startTime.clear();
            endTime.clear();
        }
    }
}
