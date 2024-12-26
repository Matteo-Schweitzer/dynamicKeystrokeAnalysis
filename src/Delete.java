import javax.swing.*;
import java.awt.*;

public class Delete extends KeyLogger {
    Main deleteFrame = new Main("Login System Demo");
    private TextField usernameInput;

    void startDeleteUI() {
        deleteFrame.setSize(new Dimension(700, 600));
        deleteFrame.setResizable(false);
        deleteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deleteFrame.addWindowListener(new WindowOperations());

        JPanel username = UI.createUsernameUI();
        JPanel password = UI.createPasswordUI();
        Database.passwordInput.addKeyListener(this);


        //password enter logic
        Label info = new Label("The entered password has to be entered in one go, without mistakes, for security reasons. If a mistake happens, delete the whole input and start again");
        alert = new Label("");
        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> {
            usernameInput = (TextField) username.getComponent(2);
            String finalPassword = Database.passwordInput.getText();
            for(int j = 0; j < endTime.size(); j++) {
                dwellIntervals.add(endTime.get(j) - startTime.get(j));
                if(j < endTime.size() - 1) {
                    flightIntervals.add(startTime.get(j + 1) - endTime.get(j));
                }
            }
            User user = Database.getUser(usernameInput.getText());
            if(user != null && !user.username().equals(Main.currUser.username())) {
                alert.setText("You can only delete the user you're logged in as!");
            } else if(usernameInput.getText().isEmpty() || finalPassword.isEmpty()) {
                alert.setText("All boxes need to be filled!");
            } else if(user != null && !finalPassword.equals(user.password())) {
                alert.setText("Entered password wrong!");
            } else if(user != null){
                boolean remove = true;
                for (int i = 0; i < dwellIntervals.size(); i++) {
                    if (dwellIntervals.get(i) > user.dwellIntervals().get(i) + Database.DWELL_THRESHOLD || dwellIntervals.get(i) < user.dwellIntervals().get(i) - Database.DWELL_THRESHOLD) {
                        alert.setText("Delete denied!");
                        Database.passwordInput.setText("");
                        startTime.clear();
                        endTime.clear();
                        remove = false;
                        break;
                    }
                }
                for(int j = 0; j < flightIntervals.size(); j++) {
                    if (flightIntervals.get(j) > user.flightIntervals().get(j) + Database.FLIGHT_THRESHOLD || flightIntervals.get(j) < user.flightIntervals().get(j) - Database.FLIGHT_THRESHOLD) {
                        alert.setText("Delete denied!");
                        Database.passwordInput.setText("");
                        startTime.clear();
                        endTime.clear();
                        remove = false;
                        break;
                    }
                }

                if(remove) {
                    for(int i = 0; i < Database.users.size(); i++) {
                        if(Database.users.get(i).username().equals(usernameInput.getText())) {
                            Database.users.remove(i);
                            break;
                        }
                    }
                    Main.currUser = Database.getUser(usernameInput.getText());
                    deleteFrame.setVisible(false);
                    deleteFrame.dispose();
                    Main.loginStatus = false;
                    Main.currUser = null;
                    Main.startUI();
                }
            }
            startTime.clear();
            endTime.clear();
            dwellIntervals.clear();
            flightIntervals.clear();
        });

        JButton back = new UI().createBackButton(deleteFrame);

        //add all components to panel
        JPanel deleteComponents = new JPanel();
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));
        buttons.add(back);
        buttons.add(delete);
        deleteComponents.setLayout(new GridLayout(0,1));
        deleteComponents.add(username);
        deleteComponents.add(password);
        deleteComponents.add(info);
        deleteComponents.add(alert);
        deleteComponents.add(buttons);

        deleteFrame.add(deleteComponents);
        deleteFrame.pack();
        deleteFrame.setVisible(true);
    }
}