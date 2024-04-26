import java.util.Scanner;

public class UI {
    public static void main(String[] args) {
        System.out.println("Welcome to this demo for keystroke analysis!\nChoose which action you want to perform by typing the corresponding letter:");
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
        }

    }
}
