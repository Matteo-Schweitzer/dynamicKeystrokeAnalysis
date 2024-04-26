import java.util.Scanner;

public class Register {

    static String defineUsername() {
        boolean usernameInvalid = true;
        String username = null;
        while(usernameInvalid) {
            usernameInvalid = false;
            System.out.print("Enter username: ");
            Scanner usernameScanner = new Scanner(System.in);
            username = usernameScanner.nextLine();
            for(User user : Database.users) {
                if(user.getUsername().equals(username)) {
                    System.out.println("Username already exists!");
                    usernameInvalid = true;
                    break;
                }
            }
        }
        return username;
    }

    static String definePassword() {
        System.out.print("Enter password: ");
        Scanner passwordScanner = new Scanner(System.in);
        return passwordScanner.nextLine();
    }

    static int[] defineTimeIntervals(String password) {
        return new int[1];
    }
}
