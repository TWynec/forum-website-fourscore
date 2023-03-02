package com.fourumscore.forum.congregations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CongregationService {

    @Autowired
    private CongregationRepo congregationRepo;

    public List<Congregation> allCongregations() {
        return (List<Congregation>) congregationRepo.findAll();
    }

    public Optional<Congregation> congregation(String title) {
        return congregationRepo.findById(title);
    }

    public String newCongregation(String title, String description) {
        if (congregation(title).isEmpty()){
            congregationRepo.save(new Congregation(title, description));
            return title + " created!";
        }
        return title + " already exists.";
    }

    public String editTitle(String oldTitle, String newTitle) {
        if (congregation(oldTitle).isPresent()) {
            if (!oldTitle.equals(newTitle)) {
                congregationRepo.updateTitle(newTitle, oldTitle);
                return oldTitle + " is now " + newTitle + "!";
            }
            return "Old title and new title cannot be the same.";
        }
        return oldTitle + " does not exist.";
    }

    public String editDescription(String title, String description) {
        if(congregation(title).isPresent()) {
            if (!description.equals(congregation(title).get().getDescription())) {
                congregationRepo.save(new Congregation(title, description));
                return title + " description updated!";
            }
            return "New description is the same as the old description.";
        }
        return title + " does not exist.";
    }

    public String deleteCongregation(String username, String title) {
        if (congregation(title).isPresent()) {
            if (username.equals("admin")) {
                congregationRepo.deleteById(title);
                return "Congregation deleted.";
            }
            return "You do not have permission to delete " + title + ".";
        }
        return title + " does not exist.";
    }
}
