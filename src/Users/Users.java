package Users;
import Profile.*;

import java.util.ArrayList;

public class Users {

    private final ArrayList<Profile> users = new ArrayList<>();

    public int checkUserExists(String username) {
        for (Profile user : users) {
            if (user.getUsername().equals(username)) {
                return users.indexOf(user);
            }
        }
        return -1;
    }

    public boolean checkEmailExists(String email) {
        for (Profile user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(Profile user) {
        users.add(user);
    }

    public Profile login(Credentials creds) {
        int userIndex = checkUserExists(creds.username);
        if (userIndex != -1) {
            if (users.get(userIndex).getPassword().equals(creds.password)) {
                System.out.println("Successfully logged in!");
                return users.get(userIndex);
            }
        }
        System.out.println("User not found; Please check your credentials and try again...");
        return null;
    }

    public Profile deleteUser(Credentials creds) {
        int userIndex = checkUserExists(creds.username);
        if (userIndex != -1) {
            if (users.get(userIndex).getPassword().equals(creds.password)) {
                System.out.println("Profile successfully deleted.");
                return users.remove(userIndex);
            }
        }
        System.out.println("User not found; Please check your credentials and try again...");
        return null;
    }

    public int getNumUsers() {
        return users.size();
    }
}
