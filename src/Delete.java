import javax.swing.*;
import java.awt.*;

public class Delete extends KeyLogger {
    UI deleteFrame = new UI("Login System Demo");
    private TextField usernameInput;
    private TextField passwordInput;

    void startDeleteUI() {
        deleteFrame.setSize(new Dimension(700, 600));
        deleteFrame.setResizable(false);
        deleteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        passwordInput = new TextField(20);
        passwordInput.addKeyListener(this);
        password.add(passwordLabel);
        password.add(passwordInput);


        //password enter logic
        Label info = new Label("The entered password has to be entered in one go, without mistakes, for security reasons. If a mistake happens, delete the whole input and start again");
        alert = new Label("");
        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> {
            String finalPassword = passwordInput.getText();
            for(int j = 0; j < endTime.size(); j++) {
                intervals.add(endTime.get(j) - startTime.get(j));
                if(j < endTime.size() - 1) {
                    dwellIntervals.add(startTime.get(j + 1) - endTime.get(j));
                }
            }
            User user = Database.getUser(usernameInput.getText());
            if(user != null && !user.username().equals(UI.currUser.username())) {
                alert.setText("You can only delete the user you're logged in as!");
            } else if(usernameInput.getText().isEmpty() || finalPassword.isEmpty()) {
                alert.setText("All boxes need to be filled!");
            } else if(user != null && !finalPassword.equals(user.password())) {
                alert.setText("Entered password wrong!");
            } else if(user != null){
                boolean remove = true;
                int counter = 0;
                for (int i = 0; i < intervals.size(); i++) {
                    if (intervals.get(i) > user.timeIntervals().get(i) + 30 || intervals.get(i) < user.timeIntervals().get(i) - 30) {
                        counter++;
                    }
                }
                for(int j = 0; j < dwellIntervals.size(); j++) {
                    if (dwellIntervals.get(j) > user.dwellIntervals().get(j) + 30 || dwellIntervals.get(j) < user.dwellIntervals().get(j) - 30) {
                        counter++;
                    }
                }
                if(counter > intervals.size()*0.2) {
                    alert.setText("Delete denied!");
                    remove = false;
                }
                if(remove) {
                    for(int i = 0; i < Database.users.size(); i++) {
                        if(Database.users.get(i).username().equals(usernameInput.getText())) {
                            Database.users.remove(i);
                            break;
                        }
                    }
                    for(User users : Database.users) {
                        if(users.username().equals(usernameInput.getText())) {
                            for(int i = 0; i < users.timeIntervals().size(); i++) {
                                users.timeIntervals().set(i, (users.timeIntervals().get(i) + intervals.get(i))/2);
                            }
                            for(int i = 0; i < users.dwellIntervals().size(); i++) {
                                users.dwellIntervals().set(i, (users.dwellIntervals().get(i) + dwellIntervals.get(i))/2);
                            }
                            break;
                        }
                    }
                    UI.currUser = Database.getUser(usernameInput.getText());
                    deleteFrame.setVisible(false);
                    deleteFrame.dispose();
                    UI.loginStatus = false;
                    UI.currUser = null;
                    UI.startUI();
                }
            }
            startTime.clear();
            endTime.clear();
            intervals.clear();
            dwellIntervals.clear();
        });

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            startTime.clear();
            endTime.clear();
            intervals.clear();
            dwellIntervals.clear();
            deleteFrame.setVisible(false);
            deleteFrame.dispose();
            UI.startUI();
        });

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