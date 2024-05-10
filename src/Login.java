import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Login implements KeyListener {

    UI loginFrame = new UI("Login System Demo");
    ArrayList<Long> startTime = new ArrayList<>();
    ArrayList<Long> endTime = new ArrayList<>();
    ArrayList<Long> intervals = new ArrayList<>();
    ArrayList<Long> dwellIntervals = new ArrayList<>();

    private TextField usernameInput;
    private TextField passwordInput;
    private Label alert;


    void startLoginUI() {
        loginFrame.setSize(new Dimension(700, 600));
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //username UI
        JPanel username = new JPanel();
        username.setLayout(new BoxLayout(username, BoxLayout.LINE_AXIS));
        username.add(Box.createHorizontalGlue());
        Label usernameLabel = new Label("Enter username:");
        usernameInput = new TextField(20);
        Label usernameInfo = new Label("");
        username.add(usernameLabel, BorderLayout.WEST);
        username.add(usernameInput);
        username.add(usernameInfo);

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
        JButton login = new JButton("Complete login");
        login.addActionListener(e -> {
            boolean checkUsername = Database.checkUsername(usernameInput.getText());
            String finalPassword = passwordInput.getText();

            for(int j = 0; j < endTime.size(); j++) {
                intervals.add(endTime.get(j) - startTime.get(j));
                if(j != endTime.size() - 1) {
                    dwellIntervals.add(startTime.get(j + 1) - endTime.get(j));
                }
            }
            User user = Database.getUser(usernameInput.getText());
            if(checkUsername) {
                alert.setText("Username does not exist!");
            } else if(usernameInput.getText().isEmpty() || finalPassword.isEmpty()) {
                alert.setText("All boxes need to be filled!");
            } else if(!finalPassword.equals(user.password())) {
                alert.setText("Entered password wrong!");
            } else {
                boolean access = true;
                for (int i = 0; i < intervals.size(); i++) {
                    if (intervals.get(i) > user.timeIntervals().get(i) + 30 || intervals.get(i) < user.timeIntervals().get(i) - 30) {
                        alert.setText("Access denied!");
                        access = false;
                        break;
                    }
                }
                if(access) {
                    for(int j = 0; j < dwellIntervals.size(); j++) {
                        if (dwellIntervals.get(j) > user.dwellIntervals().get(j) + 30 || dwellIntervals.get(j) < user.dwellIntervals().get(j) - 30) {
                            alert.setText("Access denied!");
                            access = false;
                            break;
                        }
                    }
                }
                if(access) {
                    loginFrame.setVisible(false);
                    loginFrame.dispose();
                    UI.loginStatus = true;
                    UI.startUI();
                }
            }
            startTime.clear();
            endTime.clear();
            intervals.clear();
            dwellIntervals.clear();
        });

        //add all components to panel
        JPanel registerComponents = new JPanel();
        registerComponents.setLayout(new GridLayout(0,1));
        registerComponents.add(username);
        registerComponents.add(password);
        registerComponents.add(info);
        registerComponents.add(alert);
        registerComponents.add(login);

        loginFrame.add(registerComponents);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        startTime.add(System.currentTimeMillis());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        endTime.add(System.currentTimeMillis());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == 8 || keyCode == 127) {
            alert.setText("Please delete all inputs!");
            startTime.clear();
            endTime.clear();
        }
    }
}
