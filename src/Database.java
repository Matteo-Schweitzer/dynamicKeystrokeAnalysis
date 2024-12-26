import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    static List<User> users = new ArrayList<>();
    static TextField passwordInput;
    static private final String USERDATA = "src/users.txt";
    static final int DWELL_THRESHOLD = 80;
    static final int FLIGHT_THRESHOLD = 120;

    static boolean checkUsername(String usernameInput) {
        if(usernameInput.contains(" ")) {
            return false;
        }
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

    static void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERDATA)))
        {
            for(User user : Database.users) {
                writer.write(user.username() + " " + user.password());
                writer.newLine();
                for(Long num : user.dwellIntervals()) {
                    writer.write(num.toString() +  " ");
                }
                writer.newLine();
                for(Long num : user.flightIntervals()) {
                    writer.write(num.toString() +  " ");
                }
                writer.newLine();
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Saving data not successful!");
        }
    }

    static void loadData() {
        String usernamePassword = "";
        String dwellInterval = " ";
        String flightInterval = " ";

        try (BufferedReader reader = new BufferedReader(new FileReader(USERDATA))) {
            String input = reader.readLine();
            int i = 0;
            while(input != null) {
                if(i == 0) {
                    usernamePassword = input;
                    i++;
                } else if(i == 1) {
                    dwellInterval = input;
                    i++;
                } else if(i == 2) {
                    flightInterval = input;
                    i++;
                } else if(i == 3) {
                    String[] userInfo = usernamePassword.split(" ");
                    long[] dwell = Arrays.stream(dwellInterval.split(" ")).mapToLong(Long::parseLong).toArray();
                    long[] flight;
                    try {
                        flight = Arrays.stream(flightInterval.split(" ")).mapToLong(Long::parseLong).toArray();
                    } catch(Exception e) {
                        flight = new long[0];
                    }

                    ArrayList<Long> dwellIntervals = new ArrayList<>();
                    ArrayList<Long> flightIntervals = new ArrayList<>();
                    for(int j = 0; j < dwell.length; j++) {
                        dwellIntervals.add(dwell[j]);
                        if(j < dwell.length - 1) {
                            flightIntervals.add(flight[j]);
                        }
                    }
                    Database.users.add(new User(userInfo[0], userInfo[1], dwellIntervals, flightIntervals));
                    i = 0;
                }
                input = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Loading data not successful!");
        }
    }
}