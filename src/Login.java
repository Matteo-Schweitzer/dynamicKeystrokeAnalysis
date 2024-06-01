import javax.swing.*;
import java.awt.*;

public class Login extends KeyLogger {

    UI loginFrame = new UI("Login System Demo");
    private TextField usernameInput;
    private final int DWELL_THRESHOLD = 80;
    private final int FLIGHT_THRESHOLD = 120;

    void startLoginUI() {
        loginFrame.setSize(new Dimension(700, 600));
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.addWindowListener(new WindowOperations());

        //username UI
        JPanel username = new JPanel();
        username.setLayout(new BoxLayout(username, BoxLayout.LINE_AXIS));
        username.add(Box.createHorizontalGlue());
        Label usernameLabel = new Label("Enter username:");
        usernameInput = new TextField(20);
        username.add(usernameLabel, BorderLayout.WEST);
        username.add(usernameInput);

        //password UI
        JPanel password = new JPanel();
        password.setLayout(new BoxLayout(password, BoxLayout.LINE_AXIS));
        password.add(Box.createHorizontalGlue());
        Label passwordLabel = new Label("Enter password:");
        Database.passwordInput = new TextField(20);
        Database.passwordInput.addKeyListener(this);
        password.add(passwordLabel);
        password.add(Database.passwordInput);


        //password enter logic
        Label info = new Label("The entered password has to be entered in one go, without mistakes, for security reasons. If a mistake happens, delete the whole input and start again");
        alert = new Label("");
        JButton login = new JButton("Complete login");
        login.addActionListener(e -> {
            boolean checkUsername = Database.checkUsername(usernameInput.getText());
            String finalPassword = Database.passwordInput.getText();

            for(int j = 0; j < endTime.size(); j++) {
                dwellIntervals.add(endTime.get(j) - startTime.get(j));
                if(j < endTime.size() - 1) {
                    flightIntervals.add(startTime.get(j + 1) - endTime.get(j));
                }
            }
            User user = Database.getUser(usernameInput.getText());
            if(checkUsername) {
                alert.setText("Username does not exist!");
            } else if(usernameInput.getText().isEmpty() || finalPassword.isEmpty()) {
                alert.setText("All boxes need to be filled!");
            } else if(user != null && !finalPassword.equals(user.password())) {
                alert.setText("Entered password wrong!");
            } else if(user != null) {
                boolean access = true;

                for (int i = 0; i < dwellIntervals.size(); i++) {
                    if (dwellIntervals.get(i) > user.dwellIntervals().get(i) + DWELL_THRESHOLD || dwellIntervals.get(i) < user.dwellIntervals().get(i) - DWELL_THRESHOLD) {
                        alert.setText("Access denied!");
                        Database.passwordInput.setText("");
                        startTime.clear();
                        endTime.clear();
                        access = false;
                        break;
                    }
                }
                if(access) {
                    for(int j = 0; j < flightIntervals.size(); j++) {
                        if (flightIntervals.get(j) > user.flightIntervals().get(j) + FLIGHT_THRESHOLD || flightIntervals.get(j) < user.flightIntervals().get(j) - FLIGHT_THRESHOLD) {
                            alert.setText("Access denied!");
                            Database.passwordInput.setText("");
                            startTime.clear();
                            endTime.clear();
                            access = false;
                            break;
                        }
                    }
                }

                if(access) {
                    for(User users : Database.users) {
                        if(users.username().equals(usernameInput.getText())) {
                            for(int i = 0; i < users.dwellIntervals().size(); i++) {
                                users.dwellIntervals().set(i, (users.dwellIntervals().get(i) + dwellIntervals.get(i))/2);
                            }
                            for(int i = 0; i < users.flightIntervals().size(); i++) {
                                users.flightIntervals().set(i, (users.flightIntervals().get(i) + flightIntervals.get(i))/2);
                            }
                            break;
                        }
                    }
                    UI.currUser = Database.getUser(usernameInput.getText());
                    loginFrame.setVisible(false);
                    loginFrame.dispose();
                    UI.loginStatus = true;
                    UI.startUI();
                }
            }
            startTime.clear();
            endTime.clear();
            dwellIntervals.clear();
            flightIntervals.clear();
        });

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            startTime.clear();
            endTime.clear();
            dwellIntervals.clear();
            flightIntervals.clear();
            loginFrame.setVisible(false);
            loginFrame.dispose();
            UI.startUI();
        });

        JPanel loginComponents = new JPanel();
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));
        buttons.add(back);
        buttons.add(login);
        loginComponents.setLayout(new GridLayout(0,1));
        loginComponents.add(username);
        loginComponents.add(password);
        loginComponents.add(info);
        loginComponents.add(alert);
        loginComponents.add(buttons);

        loginFrame.add(loginComponents);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }
}
