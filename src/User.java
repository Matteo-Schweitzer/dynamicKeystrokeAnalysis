import java.util.ArrayList;

public record User(String username, String password, ArrayList<Long> dwellIntervals, ArrayList<Long> flightIntervals) {
}