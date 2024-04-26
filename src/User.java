public class User {
    private String username;
    private String password;
    private int[] timeIntervals;

    public User() {
        this.username = Register.defineUsername();
        this.password = Register.definePassword();
        this.timeIntervals = Register.defineTimeIntervals(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int[] getTimeIntervals() {
        return timeIntervals;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimeIntervals(int[] timeIntervals) {
        this.timeIntervals = timeIntervals;
    }
}
