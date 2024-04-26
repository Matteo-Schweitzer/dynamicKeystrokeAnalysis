import java.util.Scanner;

public class Login {

    static String getUsername() {
        System.out.print("Enter username: ");
        Scanner usernameScanner = new Scanner(System.in);
        return usernameScanner.nextLine();
    }

    static String getPassword() {
        System.out.print("Enter password: ");
        Scanner usernameScanner = new Scanner(System.in);
        return usernameScanner.nextLine();
    }

    static void login() {
        String username = getUsername();
        String password = getPassword();
        for(User user : Database.users) {
            if(user.getUsername().equals(username)) {
                if(user.getPassword().equals(password)) {
                    System.out.println("Login successful!");
                    return;
                }
                System.out.println("Login failed!");
            }
        }
    }
}
