import java.util.ArrayList;

public record User(String username, String password, ArrayList<Long> timeIntervals, ArrayList<Long> dwellIntervals) {
}
