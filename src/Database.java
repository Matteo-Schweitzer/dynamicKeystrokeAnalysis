import java.util.ArrayList;
import java.util.List;

public class Database {
    static List<User> users = new ArrayList<>();

    static void deleteUser() {
        String username = Login.getUsername();
        String password = Login.getPassword();
        int i = 0;
        for(User user : Database.users) {
            if(user.getUsername().equals(username)) {
                if(user.getPassword().equals(password)) {
                    System.out.println("Delete successful!");
                    Database.users.remove(i);
                    return;
                }
                System.out.println("Login failed!");
            }
            i++;
        }
    }
}
