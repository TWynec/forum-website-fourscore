import Post.*;
import Profile.*;
import Users.*;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Year;
import java.time.YearMonth;
import java.util.Scanner;

public class Main {

    static Profile loggedIn = null;
    static Users users = new Users();
    static int menuSize = 7;
    static Scanner in = new Scanner(System.in);
    static boolean again = true;


    // Could use args to pass in a path to a file holding an arraylist of users
    public static void main(String[] args) throws MalformedURLException {
        users.addUser(new Profile("ted", "ted", "ted@ted.ted", "2/2/2002", "I'm ted", null));
        run();
    }

    public static void run() throws MalformedURLException {

        int choice = selection();
        Profile temp = null;
        switch (choice) {
            case 1:
                Profile newUser = createProfile();
                users.addUser(newUser);
                System.out.println("Your profile has been created! Logging you in...");
                loggedIn = users.login(new Credentials(newUser.getUsername(), newUser.getPassword()));
                break;
            case 2:
                if (loggedIn != null) {
                    System.out.println("Currently logged in as " + loggedIn.getUsername());
                    break;
                }
                if (users.getNumUsers() > 0) {
                    while (temp == null && again) {
                        temp = users.login(new Credentials(getUsername(), getPassword()));
                        if (temp != null) again = false;
                        else {
                            if (again) {
                                System.out.println("Would you like to try again? (Type y or n):");
                                String yorn = in.nextLine();
                                again = yorn.equalsIgnoreCase("y") || yorn.equalsIgnoreCase("yes");
                            }
                        }
                    }
                    loggedIn = temp;
                }
                else System.out.println("There are no registered users. Please create a new profile!");
                break;
            case 3:
                System.out.println("not here yet...");
                break;
            case 4:
                if (loggedIn != null) {
                    loggedIn = null;
                    System.out.println("Successfully logged out. See you soon!");
                }
                else System.out.println("No user is logged in. Please make a new selection!");
                break;
            case 5:
                if (users.getNumUsers() > 0) {
                    while (again) {
                        temp = users.deleteUser(new Credentials(getUsername(), getPassword()));
                        if (temp != null) {
                            again = false;
                            temp = null;
                        }
                        else {
                            if (again) {
                                System.out.println("Would you like to try again? (Type y or n):");
                                String yorn = in.nextLine();
                                again = yorn.equalsIgnoreCase("y") || yorn.equalsIgnoreCase("yes");
                            }
                            temp = loggedIn;
                        }
                    }
                    loggedIn = temp;
                }
                else System.out.println("There are no registered users. There's nothing to delete!");
                break;
            case 6:
                if (loggedIn == null) {
                    System.out.println("You must be logged in to edit your profile");
                }
                else {
                    System.out.println("Entering profile editor...");
                    loggedIn.editProfile(0, users);
                }
                break;
            default:
                System.out.println("Thanks for visiting our forum!");
                loggedIn = null;
                return;
        }
        again = true;
        run();
    }

    public static Profile createProfile() {
        String username = createUsername();
        String password = getPassword();
        String email = getEmail();
        String dob = getDOB();
        try {
            return new Profile(username, password, email, dob, "Tell us about you!", new ImageIcon(new URL("https://st3.depositphotos.com/1767687/16607/v/450/depositphotos_166074422-stock-illustration-default-avatar-profile-icon-grey.jpg")));
        }
        catch (MalformedURLException e) {
            System.out.println("Invalid Avatar URL");
            return new Profile(username, password, email, dob, "Tell us about you!", null);
        }
    }

    public static String getUsername() {
        System.out.println("Please enter a valid username:");
        String username = in.nextLine();
        if (username.length() < 1) return getUsername();
        else {
            if (users.checkUserExists(username) == -1) {
                System.out.println(username + " is not registered to a user.\nWould you like to try a different username? (Type y or n):");
                String yorn = in.nextLine();
                again = yorn.equalsIgnoreCase("y") || yorn.equalsIgnoreCase("yes");
                if (!again) return "";
                else return getUsername();
            }
            else return username;
        }
    }

    public static String createUsername() {
        System.out.println("Please enter a valid username:");
        String username = in.nextLine();
        if (username.length() < 1) return createUsername();
        else if (users.checkUserExists(username) != -1) {
            System.out.println("User already exists...");
            return createUsername();
        }
        else return username;
    }

    public static String getPassword() {
        if (again) {
            System.out.println("Please enter a valid password:");
            String password = in.nextLine();
            if (password.length() < 1) return getPassword();
            else return password;
        }
        else return "";
    }

    public static String getEmail() {
        System.out.println("Please enter a valid email (this will be used for password reset):");
        String email = in.nextLine();
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        if (!(email.matches(regex))) return getEmail();
        else if (users.checkEmailExists(email)) {
            System.out.println("Email already in use...");
            return getEmail();
        }
        else return email;
    }

    public static String getDOB() {
        System.out.println("Please enter your date of birth");
        int dobYear = getDOBYear();
        int dobMonth = getDOBMonth();
        int dobDay = getDOBDay(dobMonth, dobYear);
        return String.format("%d/%d/%d", dobMonth, dobDay, dobYear);
    }

    private static int getDOBYear() {
        System.out.println("Year (number 1907 - " + (Year.now().getValue() - 1) + "):");
        try {
            int year = Integer.parseInt(in.nextLine());
            if (year > 1906 && year < Year.now().getValue()) return year;
            else {
                System.out.println("Year must be between 1907 and " + (Year.now().getValue() - 1));
                return getDOBYear();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter an integer");
            return getDOBYear();
        }
    }

    private static int getDOBMonth() {
        System.out.println("Month (number 1 - 12):");
        try {
            int month = Integer.parseInt(in.nextLine());
            if (month > 0 && month < 13) return month;
            else {
                System.out.println("Month must be between 1 and 12");
                return getDOBMonth();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter an integer");
            return getDOBMonth();
        }
    }

    private static int getDOBDay(int month, int year) {
        System.out.println("Day (number 1 - " + YearMonth.of(year, month).lengthOfMonth() + "):");
        try {
            int day = Integer.parseInt(in.nextLine());
            if (YearMonth.of(year, month).isValidDay(day)) {
                return day;
            } else {
                System.out.println("Day must be between 1 and " + YearMonth.of(year, month).lengthOfMonth());
                return getDOBDay(month, year);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter an integer");
            return getDOBDay(month, year);
        }
    }

    private static int selection() {
        printMenu();
        try {
            int choice = Integer.parseInt(in.nextLine());
            if (choice > 0 && choice <= menuSize) return choice;
            else {
                System.out.println("Choice must be between 1 and " + menuSize);
                return selection();
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter an integer");
            return selection();
        }
    }

    private static void printMenu() {
        System.out.println(
            "1) Create New Profile\n" +
            "2) Log In / View User\n" +
            "3) Create New Post\n" +
            "4) Log Out\n" +
            "5) Delete Profile\n" +
            "6) Edit Profile\n" +
            "7) Quit\n" +
            "Please make a valid selection:");
    }
}