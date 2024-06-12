import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{

    static boolean loginStatus = false;
    static User currUser = null;
    static Label alert = new Label("");

    public Main(String name) {
        super(name);
    }

    public static void main(String[] args) {
        Database.loadData();
        startUI();
    }

    static void startUI() {
        Main mainFrame = new Main("Login System Demo");
        alert.setText("");
        mainFrame.setPreferredSize(new Dimension(300, 300));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addWindowListener(new WindowOperations());

        JButton loginButton = new JButton("Login with existing user");
        loginButton.addActionListener(e -> {
            if(loginStatus) {
                alert.setText("Log out before logging in with different user!");
            } else {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                new Login().startLoginUI();
            }
        });

        JButton registerButton = new JButton("Register new user");
        registerButton.addActionListener(e -> {
            if(loginStatus) {
                alert.setText("Log out before registering a different user!");
            } else {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                new Register().startRegisterUI();
            }
        });

        JButton deleteButton = new JButton("Delete user");
        deleteButton.addActionListener(e -> {
            if(!loginStatus) {
                alert.setText("Log in with user before deleting it");
            } else {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                new Delete().startDeleteUI();
            }

        });

        Label loginStatusLabel = new Label("");
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            loginStatus = false;
            currUser = null;
            loginStatusLabel.setText("Logged out");
        });

        JButton exitButton = new JButton("Exit program");
        exitButton.addActionListener(e -> {
            Database.saveData();
            System.exit(0);
        });

        if(loginStatus) {
            loginStatusLabel.setText("Logged in as: " + currUser.username());
        } else {
            loginStatusLabel.setText("Logged out");
        }

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(loginStatusLabel);
        buttons.add(alert);
        buttons.add(loginButton);
        buttons.add(registerButton);
        buttons.add(deleteButton);
        buttons.add(logoutButton);
        buttons.add(exitButton);

        Container buttonPane = mainFrame.getContentPane();
        buttonPane.add(buttons, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}