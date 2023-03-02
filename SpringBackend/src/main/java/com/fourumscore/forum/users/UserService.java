package com.fourumscore.forum.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> allUsers() {
        return (List<User>) userRepo.findAll();
    }

    public String login(String username, String password) {
        Optional<User> user = userRepo.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) return username;
            return "Incorrect password.";
        }
        return username + " does not exist.";
    }

    public Optional<User> user(String username) {
        return userRepo.findById(username);
    }

    public boolean email(String email) { return userRepo.existsByEmail(email); }

    public String newUser(String username, String password, String email) {
        if (user(username).isEmpty()) {
            if (!userRepo.existsByEmail(email)) {
                if (email.matches("^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@"
                + "[^-][A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*(\\.[A-Za-z]{2,})$")) {
                    userRepo.save(new User(username, password, email));
                    return "User created! Welcome, " + username + "!";
                }
                return "Please enter a valid email.";
            }
            return email + " is already in use.";
        }
        return username + " already exists.";
    }

    public String editUsername(String oldUsername, String newUsername, String password) {
        if (user(oldUsername).isPresent()) {
            if (user(oldUsername).get().getPassword().equals(password)) {
                if (!oldUsername.equals(newUsername)) {
                    userRepo.updateUsername(newUsername, oldUsername);
                    return oldUsername + " is now " + newUsername + "!";
                }
                return "Old username and new username cannot be the same.";
            }
            return "Incorrect password.";
        }
        return oldUsername + " does not exist.";
    }

    public String editPassword(String username, String oldPassword, String newPassword) {
        if (user(username).isPresent()) {
            if (user(username).get().getPassword().equals(oldPassword)) {
                if (!oldPassword.equals(newPassword)) {
                    User user = user(username).get();
                    userRepo.save(new User(username, newPassword, user.getEmail(), user.getBio(), user.getPfp()));
                    return "Password updated!";
                }
                return "Old password and new password cannot be the same.";
            }
            return "Incorrect password.";
        }
        return username + " does not exist.";
    }

    public String editEmail(String username, String password, String oldEmail, String newEmail) {
        if (user(username).isPresent()) {
            if (user(username).get().getPassword().equals(password)) {
                if (!oldEmail.equals(newEmail)) {
                    if (!email(newEmail)) {
                        User user = user(username).get();
                        userRepo.save(new User(username, password, newEmail, user.getBio(), user.getPfp()));
                        return "Your email is now + " + newEmail + "!";
                    }
                    return "New email already in use.";
                }
                return "Old email and new email cannot be the same.";
            }
            return "Incorrect password.";
        }
        return username + " does not exist.";
    }

    public String editBio(String username, String password, String oldBio, String newBio) {
        if (user(username).isPresent()) {
            if (user(username).get().getPassword().equals(password)) {
                if (!oldBio.equals(newBio)) {
                    User user = user(username).get();
                    userRepo.save(new User(username, password, user.getEmail(), newBio, user.getPfp()));
                    return "Your bio has been updated!";
                }
                return "Old bio and new bio cannot be the same.";
            }
            return "Incorrect password.";
        }
        return username + " does not exist.";
    }

    public String editAvatar(String username, String password, String oldAvatar, String newAvatar) {
        if (user(username).isPresent()) {
            if (user(username).get().getPassword().equals(password)) {
                if (!oldAvatar.equals(newAvatar)) {
                    User user = user(username).get();
                    userRepo.save(new User(username, password, user.getEmail(), user.getBio(), newAvatar));
                    return "Profile picture updated!";
                }
                return "Old avatar and new avatar cannot be the same.";
            }
            return "Incorrect password.";
        }
        return username + " does not exist.";
    }

    public String dropUser(String username, String password) {
        if (user(username).isPresent()) {
            if (user(username).get().getPassword().equals(password) || username.equals("admin")) {
                userRepo.deleteById(username);
                return "User deleted.";
            }
            return "Incorrect password.";
        }
        return username + " does not exist.";
    }
}