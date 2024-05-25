import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Register extends KeyLogger {

    ArrayList<Long> dwellIntervals = new ArrayList<>();
    ArrayList<Long> flightIntervals = new ArrayList<>();

    UI registerFrame = new UI("Login System Demo");
    private TextField usernameInput;
    private TextField chosenPassword;

    void startRegisterUI() {
        registerFrame.setSize(new Dimension(700, 600));
        registerFrame.setResizable(false);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.addWindowListener(new WindowOperations());

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
        chosenPassword = new TextField(20);
        password.add(passwordLabel);
        password.add(chosenPassword);

        //password reenter UI
        JPanel passwordReenter = new JPanel();
        passwordReenter.setLayout(new BoxLayout(passwordReenter, BoxLayout.LINE_AXIS));
        passwordReenter.add(Box.createHorizontalGlue());
        Label passwordReenterLabel = new Label("Enter password again:");
        Database.passwordInput = new TextField(22);
        Database.passwordInput.addKeyListener(this);
        passwordReenter.add(passwordReenterLabel);
        passwordReenter.add(Database.passwordInput);

        //password reenter logic
        Label info = new Label("The re-entered password has to be entered in one go, without mistakes, for security reasons. If a mistake happens, delete the whole input and start again");
        alert = new Label("");
        JButton register = new JButton("Complete registration");
        register.addActionListener(e -> {
            boolean checkUsername = Database.checkUsername(usernameInput.getText());
            String finalPassword = chosenPassword.getText();
            String finalReenterPassword = Database.passwordInput.getText();

            for(int j = 0; j < endTime.size(); j++) {
                dwellIntervals.add(endTime.get(j) - startTime.get(j));
                if(j < endTime.size() - 1) {
                    flightIntervals.add(startTime.get(j + 1) - endTime.get(j));
                }
            }

            if(!checkUsername) {
                alert.setText("Username not available!");
            } else if(usernameInput.getText().isEmpty() || finalPassword.isEmpty()) {
                alert.setText("All boxes need to be filled!");
            } else if(!finalPassword.equals(finalReenterPassword)) {
                alert.setText("Re-entered password wrong!");
            } else if (finalPassword.contains(" ")) {
                alert.setText("Password must not contain spaces!");
            } else {
                Database.users.add(new User(usernameInput.getText(), finalPassword, dwellIntervals, flightIntervals));
                registerFrame.setVisible(false);
                registerFrame.dispose();
                UI.startUI();
            }
            startTime.clear();
            endTime.clear();
        });

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            startTime.clear();
            endTime.clear();
            dwellIntervals.clear();
            flightIntervals.clear();
            registerFrame.setVisible(false);
            registerFrame.dispose();
            UI.startUI();
        });

        //add all components to panel
        JPanel registerComponents = new JPanel();
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 0));
        buttons.add(back);
        buttons.add(register);
        registerComponents.setLayout(new GridLayout(0,1));
        registerComponents.add(username);
        registerComponents.add(password);
        registerComponents.add(passwordReenter);
        registerComponents.add(info);
        registerComponents.add(alert);
        registerComponents.add(buttons);

        registerFrame.add(registerComponents);
        registerFrame.pack();
        registerFrame.setVisible(true);
    }
}
