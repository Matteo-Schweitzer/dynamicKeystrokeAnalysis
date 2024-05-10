import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {

    static boolean loginStatus = false;

    public UI(String name) {
        super(name);
    }

    public static void main(String[] args) {
        /*System.out.println("Welcome to this demo for keystroke analysis!\nChoose which action you want to perform by typing the corresponding letter:");
        System.out.println("""
                L - Login
                R - Register
                D - Delete
                X - Exit program""");
        Scanner actionScanner = new Scanner(System.in);
        String action = actionScanner.nextLine();
        while(true) {
            if(action.equalsIgnoreCase("L")) {
                Login.login();
            } else if(action.equalsIgnoreCase("R")) {
                Database.users.add(new User());
            } else if(action.equalsIgnoreCase("D")) {
                Database.deleteUser();
            } else if(action.equalsIgnoreCase("X")) {
                return;
            } else {
                System.out.println("Invalid Input");
            }
            action = actionScanner.nextLine();
        }*/
        startUI();
    }

    static void startUI() {
        UI mainFrame = new UI("Login System Demo");
        mainFrame.setPreferredSize(new Dimension(300, 300));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton loginButton = new JButton("Login with existing user");
        loginButton.addActionListener(e -> {
            mainFrame.setVisible(false);
            mainFrame.dispose();
            new Login().startLoginUI();
        });

        JButton registerButton = new JButton("Register new user");
        registerButton.addActionListener(e -> {
            mainFrame.setVisible(false);
            mainFrame.dispose();
            new Register().startRegisterUI();
        });

        JButton deleteButton = new JButton("Delete user");
        deleteButton.addActionListener(e -> {
            mainFrame.setVisible(false);
            mainFrame.dispose();

        });

        Label loginStatusLabel = new Label("");
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            loginStatus = false;
            loginStatusLabel.setText("Logged out");
        });

        JButton exitButton = new JButton("Exit program");
        exitButton.addActionListener(e -> System.exit(0));

        if(loginStatus) {
            loginStatusLabel.setText("Logged in");
        } else {
            loginStatusLabel.setText("Logged out");
        }

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(loginStatusLabel);
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
