package com.fourumscore.forum.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String[][] getAllUsers() {
        List<User> users = userService.allUsers();
        if (users.size() > 0) {
            String[][] usersArr = new String[users.size()][];
            for (int i = 0; i < users.size(); i++) {
                User present = users.get(i);
                usersArr[i] = new String[] { present.getPfp(), present.getUsername() };
            }
            return usersArr;
        }
        else return new String[][] { { "There are no users. Create the first profile now!" } };
    }

    @GetMapping("/login/{username}/{password}")
    public String login(@PathVariable String username, @PathVariable String password) {
        return userService.login(username, password);
    }

    @GetMapping("/newUser/{username}/{password}/{email}")
    public String createNewUser(@PathVariable String username, @PathVariable String password, @PathVariable String email) {
        return userService.newUser(username, password, email);
    }

    @GetMapping("/editUserUsername/{oldUsername}/{newUsername}/{password}")
    public String editUserUsername(@PathVariable String oldUsername, @PathVariable String newUsername, @PathVariable String password) {
        return userService.editUsername(oldUsername, newUsername, password);
    }

    @GetMapping("/editUserPassword/{username}/{oldPassword}/{newPassword}")
    public String editUserPassword(@PathVariable String username, @PathVariable String oldPassword, @PathVariable String newPassword) {
        return userService.editPassword(username, oldPassword, newPassword);
    }

    @GetMapping("/editUserEmail/{username}/{password}/{oldEmail}/{newEmail}")
    public String editUserEmail(@PathVariable String username, @PathVariable String password, @PathVariable String oldEmail, @PathVariable String newEmail) {
        return userService.editEmail(username, password, oldEmail, newEmail);
    }

    @GetMapping("/editUserBio/{username}/{password}/{oldBio}/{newBio}")
    public String editUserBio(@PathVariable String username, @PathVariable String password, @PathVariable String oldBio, @PathVariable String newBio) {
        return userService.editBio(username, password, oldBio, newBio);
    }

    @GetMapping("/editUserAvatar/{username}/{password}/{oldAvatar}/{newAvatar}")
    public String editUserAvatar(@PathVariable String username, @PathVariable String password, @PathVariable String oldAvatar, @PathVariable String newAvatar) {
        return userService.editAvatar(username, password, oldAvatar, newAvatar);
    }

    @GetMapping("/deleteUser/{username}/{password}")
    public String deleteUser(@PathVariable String username, @PathVariable String password) {
        return userService.dropUser(username, password);
    }
}