import javax.swing.*;
import java.awt.*;

public class UI extends KeyLogger {
    static JPanel createUsernameUI() {
        JPanel username = new JPanel();
        username.setLayout(new BoxLayout(username, BoxLayout.LINE_AXIS));
        username.add(Box.createHorizontalGlue());
        Label usernameLabel = new Label("Enter username:");
        TextField usernameInput = new TextField(20);
        username.add(usernameLabel, BorderLayout.WEST);
        username.add(usernameInput);
        return username;
    }

    static JPanel createPasswordUI() {
        JPanel password = new JPanel();
        password.setLayout(new BoxLayout(password, BoxLayout.LINE_AXIS));
        password.add(Box.createHorizontalGlue());
        Label passwordLabel = new Label("Enter password:");
        Database.passwordInput = new TextField(20);
        password.add(passwordLabel);
        password.add(Database.passwordInput);
        return password;
    }

    static JPanel createPasswordReenterUI() {
        JPanel passwordReenter = new JPanel();
        passwordReenter.setLayout(new BoxLayout(passwordReenter, BoxLayout.LINE_AXIS));
        passwordReenter.add(Box.createHorizontalGlue());
        Label passwordReenterLabel = new Label("Enter password again:");
        Database.passwordInput = new TextField(22);
        passwordReenter.add(passwordReenterLabel);
        passwordReenter.add(Database.passwordInput);
        return passwordReenter;
    }

    JButton createBackButton(JFrame curFrame) {
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            startTime.clear();
            endTime.clear();
            dwellIntervals.clear();
            flightIntervals.clear();
            curFrame.setVisible(false);
            curFrame.dispose();
            Main.startUI();
        });
        return back;
    }

    JPanel generateUI(JFrame curFrame, JButton done, Label info) {
        JPanel components = new JPanel();
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));
        buttons.add(new UI().createBackButton(curFrame));
        buttons.add(done);
        components.setLayout(new GridLayout(0,1));
        components.add(createUsernameUI());
        components.add(createPasswordUI());
        components.add(info);
        components.add(alert);
        components.add(buttons);
        return components;
    }
}
