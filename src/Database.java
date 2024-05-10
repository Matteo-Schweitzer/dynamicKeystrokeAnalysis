import java.util.ArrayList;
import java.util.List;

public class Database {
    static List<User> users = new ArrayList<>();

    /*static void deleteUser() {
        String username = Login.getUsername();
        String password = Login.getPassword();
        int i = 0;
        for(User user : Database.users) {
            if(user.username().equals(username)) {
                if(user.password().equals(password)) {
                    System.out.println("Delete successful!");
                    Database.users.remove(i);
                    return;
                }
                System.out.println("Delete failed!");
            }
            i++;
        }
    }*/

    static boolean checkUsername(String usernameInput) {
        for(User user : Database.users) {
            if(user.username().equals(usernameInput)) {
                return false;
            }
        }
        return true;
    }

    static User getUser(String username) {
        for(User user : Database.users) {
            if(user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
