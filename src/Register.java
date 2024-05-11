import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Register extends KeyLogger {

    ArrayList<Long> intervals = new ArrayList<>();
    ArrayList<Long> dwellIntervals = new ArrayList<>();

    UI registerFrame = new UI("Login System Demo");
    private TextField usernameInput;
    private TextField passwordInput;
    private TextField passwordReenterInput;

    void startRegisterUI() {
        registerFrame.setSize(new Dimension(700, 600));
        registerFrame.setResizable(false);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
        password.add(passwordLabel);
        password.add(passwordInput);

        //password reenter UI
        JPanel passwordReenter = new JPanel();
        passwordReenter.setLayout(new BoxLayout(passwordReenter, BoxLayout.LINE_AXIS));
        passwordReenter.add(Box.createHorizontalGlue());
        Label passwordReenterLabel = new Label("Enter password again:");
        passwordReenterInput = new TextField(22);
        passwordReenterInput.addKeyListener(this);
        passwordReenter.add(passwordReenterLabel);
        passwordReenter.add(passwordReenterInput);

        //password reenter logic
        Label info = new Label("The re-entered password has to be entered in one go, without mistakes, for security reasons. If a mistake happens, delete the whole input and start again");
        alert = new Label("");
        JButton register = new JButton("Complete registration");
        register.addActionListener(e -> {
            boolean checkUsername = Database.checkUsername(usernameInput.getText());
            String finalPassword = passwordInput.getText();
            String finalReenterPassword = passwordReenterInput.getText();

            for(int j = 0; j < endTime.size(); j++) {
                intervals.add(endTime.get(j) - startTime.get(j));
                if(j < endTime.size() - 1) {
                    dwellIntervals.add(startTime.get(j + 1) - endTime.get(j));
                }
            }

            if(!checkUsername) {
                alert.setText("Username not available!");
            } else if(usernameInput.getText().isEmpty() || finalPassword.isEmpty()) {
                alert.setText("All boxes need to be filled!");
            } else if(!finalPassword.equals(finalReenterPassword)) {
                alert.setText("Re-entered password wrong!");
            } else {
                Database.users.add(new User(usernameInput.getText(), finalPassword, intervals, dwellIntervals));
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
            intervals.clear();
            dwellIntervals.clear();
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
