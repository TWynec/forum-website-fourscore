package Profile;

import Users.Users;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Year;
import java.time.YearMonth;
import java.util.Scanner;

public class Profile {

    static Scanner in = new Scanner(System.in);

    private String username;
    private String password;
    private String email;
    private String dob;
    private String bio;
    private ImageIcon pfp;

    public Profile(String username, String password, String email, String dob, String bio, ImageIcon pfp) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.bio = bio;
        this.pfp = pfp;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void editProfile(int choice, Users users) throws MalformedURLException {
        if (choice == 0) choice = selection();
        String regex;
        switch (choice) {
            case 1:
                System.out.println("Please enter a valid username:");
                String username = in.nextLine();
                if (username.length() < 1) editProfile(1, users);
                else if (users.checkUserExists(username) != -1) {
                    System.out.println("User already exists...");
                    editProfile(1, users);
                }
                else this.username = username;
                System.out.println("Username updated! Your new username is " + this.username);
                break;
            case 2:
                System.out.println("Please enter a valid password:");
                String password = in.nextLine();
                if (password.length() < 1) editProfile(2, users);
                else this.password = password;
                System.out.println("Password updated! Your new password is " + this.password);
                break;
            case 3:
                System.out.println("Please enter a valid email:");
                String email = in.nextLine();
                regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";
                if (!(email.matches(regex))) editProfile(3, users);
                else if (users.checkEmailExists(email)) {
                    System.out.println("Email already in use...");
                    editProfile(3, users);
                }
                else this.email = email;
                System.out.println("Email updated! Your new email is " + this.email);
                break;
            case 4:
                System.out.println("Please enter a valid date of birth:");
                int year = getDOBYear();
                int month = getDOBMonth();
                int day = getDOBDay(month, year);
                this.dob = String.format("%d/%d/%d", month, day, year);
                System.out.println("Date of birth updated! Your new date of birth is " + this.dob);
                break;
            case 5:
                System.out.println("Please enter a new bio:");
                String bio = in.nextLine();
                if (bio.length() < 1) editProfile(5, users);
                else this.bio = bio;
                System.out.println("Bio updated! Your new bio says:\n" + this.bio);
                break;
            case 6:
                System.out.println("Please enter the url to your new avatar:");
                String url = in.nextLine();
                regex = "[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)\n";
                if (!(url.matches(regex))) editProfile(6, users);
                else this.pfp = new ImageIcon(new URL(url));
                System.out.println("pfp updated! Your new pfp is at " + this.pfp.getImage());
                break;
            default:
                System.out.println("Exiting profile editor...");
                return;
        }
        editProfile(0, users);
    }

    private int getDOBYear() {
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

    private int getDOBMonth() {
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

    private int getDOBDay(int month, int year) {
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
            if (choice > 0 && choice < 8) return choice;
            else {
                System.out.println("Choice must be between 1 and 8");
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
            "1) Edit Username\n" +
            "2) Edit Password\n" +
            "3) Edit Email\n" +
            "4) Edit Date of Birth\n" +
            "5) Edit Bio\n" +
            "6) Edit Avatar\n" +
            "7) Quit\n" +
            "Please make a valid selection:");
    }
}