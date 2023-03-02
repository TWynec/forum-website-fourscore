package com.fourumscore.forum.congregations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/congregations")
public class CongregationController {

    @Autowired
    private CongregationService congregationService;

    @GetMapping
    public String[][] getAllCongregations() {
        List<Congregation> congregations = congregationService.allCongregations();
        if (congregations.size() > 0) {
            String[][] congregationsArr = new String[congregations.size()][];
            for (int i = 0; i < congregations.size(); i++) {
                Congregation present = congregations.get(i);
                congregationsArr[i] = new String[] { present.getTitle(), present.getDescription() };
            }
            return congregationsArr;
        }
        else return new String[][] { { "There are no congregations. Be the first to make one!" } };
    }

    @GetMapping("/newCongregation/{title}/{description}")
    public String createNewCongregation(@PathVariable String title, @PathVariable String description) {
        return congregationService.newCongregation(title, description);
    }

    @GetMapping("/editCongregationTitle/{oldTitle}/{newTitle}")
    public String editCongregationTitle(@PathVariable String oldTitle, @PathVariable String newTitle) {
        return congregationService.editTitle(oldTitle, newTitle);
    }

    @GetMapping("/editDescription/{title}/{description}")
    public String editCongregationDescription(@PathVariable String title, @PathVariable String description) {
        return congregationService.editDescription(title, description);
    }

    @GetMapping("deleteCongregation/{username}/{title}")
    public String deleteExistingCongregation(@PathVariable String username, @PathVariable String title) {
        return congregationService.deleteCongregation(username, title);
    }
}