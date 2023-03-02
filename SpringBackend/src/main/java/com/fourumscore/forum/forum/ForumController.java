package com.fourumscore.forum.forum;

import com.fourumscore.forum.comments.CommentController;
import com.fourumscore.forum.congregations.CongregationService;
import com.fourumscore.forum.posts.PostController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping
public class ForumController {

    @Autowired
    private CongregationService congregationService;
    @Autowired
    private PostController postController;
    @Autowired
    private CommentController commentController;

    @GetMapping
    public String[][] getCongregation() {
        String title = "Welcome";
        if (congregationService.congregation(title).isPresent()) {
            String[][] posts = postController.getPosts(title);
            if (posts[0].length > 1) {
                ArrayList<String[]> postsList = new ArrayList<>();
                Collections.addAll(postsList, posts);
                for (String[] post : posts) {
                    int postIndex = postsList.indexOf(post);
                    String postTitle = post[1];
                    String[][] comments = commentController.getComments(title, postTitle);
                    for (int j = 0; j < comments.length; j++) {
                        postsList.add(postIndex + j + 1, comments[j]);
                    }
                    postsList.add(postIndex + 1, new String[] { "Comments:" });
                }
                String[][] ret = new String[postsList.size()][];
                for (int k = 0; k < postsList.size(); k++) ret[k] = postsList.get(k);
                return ret;
            }
            else return new String[][] { posts[0] };
        }
        else return new String[][] { { "Main page does not exist." } };
    }
}