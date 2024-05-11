import java.util.ArrayList;
import java.util.List;

public class Database {
    static List<User> users = new ArrayList<>();

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
