import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Register extends KeyLogger {

    ArrayList<Long> dwellIntervals = new ArrayList<>();
    ArrayList<Long> flightIntervals = new ArrayList<>();

    Main registerFrame = new Main("Login System Demo");
    private TextField usernameInput;
    private TextField chosenPassword;

    void startRegisterUI() {
        registerFrame.setSize(new Dimension(700, 600));
        registerFrame.setResizable(false);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.addWindowListener(new WindowOperations());

        JPanel username = UI.createUsernameUI();
        JPanel password = UI.createPasswordUI();
        JPanel passwordReenter = UI.createPasswordReenterUI();
        Database.passwordInput.addKeyListener(this);

        //password reenter logic
        Label info = new Label("The re-entered password has to be entered in one go, without mistakes, for security reasons. If a mistake happens, delete the whole input and start again");
        alert = new Label("");
        JButton register = new JButton("Complete registration");
        register.addActionListener(e -> {
            usernameInput = (TextField) username.getComponent(2);
            chosenPassword = (TextField) password.getComponent(2);
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
                Main.startUI();
            }
            startTime.clear();
            endTime.clear();
        });

        JButton back = new UI().createBackButton(registerFrame);

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