import javax.swing.*;
import java.awt.*;

public class Login extends KeyLogger {

    Main loginFrame = new Main("Login System Demo");
    private TextField usernameInput;

    void startLoginUI() {
        loginFrame.setSize(new Dimension(700, 600));
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.addWindowListener(new WindowOperations());

        JPanel username = UI.createUsernameUI();
        JPanel password = UI.createPasswordUI();
        Database.passwordInput.addKeyListener(this);

        //password enter logic
        Label info = new Label("The entered password has to be entered in one go, without mistakes, for security reasons. If a mistake happens, delete the whole input and start again");
        alert = new Label("");
        JButton login = new JButton("Complete login");
        login.addActionListener(e -> {
            usernameInput = (TextField) username.getComponent(2);
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
                    if (dwellIntervals.get(i) > user.dwellIntervals().get(i) + Database.DWELL_THRESHOLD || dwellIntervals.get(i) < user.dwellIntervals().get(i) - Database.DWELL_THRESHOLD) {
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
                        if (flightIntervals.get(j) > user.flightIntervals().get(j) + Database.FLIGHT_THRESHOLD || flightIntervals.get(j) < user.flightIntervals().get(j) - Database.FLIGHT_THRESHOLD) {
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
                    Main.currUser = Database.getUser(usernameInput.getText());
                    loginFrame.setVisible(false);
                    loginFrame.dispose();
                    Main.loginStatus = true;
                    Main.startUI();
                }
            }
            startTime.clear();
            endTime.clear();
            dwellIntervals.clear();
            flightIntervals.clear();
        });

        JButton back = new UI().createBackButton(loginFrame);

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
