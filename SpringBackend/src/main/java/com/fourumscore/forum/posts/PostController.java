package com.fourumscore.forum.posts;

import com.fourumscore.forum.congregations.CongregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CongregationService congregationService;

    @GetMapping("{congregation}")
    public String[][] getPosts(@PathVariable String congregation) {
        if (congregationService.congregation(congregation).isPresent()) {
            List<Post> posts = postService.postsInCongregation(congregation);
            if (posts.size() > 0) {
                String[][] postsArr = new String[posts.size()][];
                for (int i = 0; i < posts.size(); i++) {
                    Post present = posts.get(i);
                    postsArr[i] = new String[] { present.getPostId().getCongregation(), present.getPostId().getTitle(), present.getAuthor(), present.getPost(), String.valueOf(present.getLikes()), String.valueOf(present.getDislikes()) };
                }
                return postsArr;
            }
            else return new String[][] { { "There are no posts in " + congregation + ". Be the first to make one!" } };
        }
        else return new String[][] { { congregation + " does not exist. But you can make it ;)" } };
    }

    @GetMapping("/createNewPost/{congregation}/{author}/{password}/{title}/{post}")
    public String createNewPost(@PathVariable String congregation, @PathVariable String author, @PathVariable String password, @PathVariable String title, @PathVariable String post) {
        return postService.createPost(congregation, title, author, password, post);
    }

    @GetMapping("/editPostTitle/{congregation}/{username}/{password}/{oldTitle}/{newTitle}")
    public String editPostTitle(@PathVariable String congregation, @PathVariable String username, @PathVariable String password, @PathVariable String oldTitle, @PathVariable String newTitle) {
        return postService.editTitle(congregation, username, password, oldTitle, newTitle);
    }

    @GetMapping("/editPostBody/{congregation}/{username}/{password}/{title}/{newPost}")
    public String editPostBody(@PathVariable String congregation, @PathVariable String username, @PathVariable String password, @PathVariable String title, @PathVariable String newPost) {
        return postService.editPost(congregation, username, password, title, newPost);
    }

    @GetMapping("/userLikePost/{congregation}/{title}/{username}/{password}")
    public String userLikePost(@PathVariable String congregation, @PathVariable String title, @PathVariable String username, @PathVariable String password) {
        return postService.likePost(congregation, title, username, password);
    }

    @GetMapping("/userDislikePost/{congregation}/{title}/{username}/{password}")
    public String userDislikePost(@PathVariable String congregation, @PathVariable String title, @PathVariable String username, @PathVariable String password) {
        return postService.dislikePost(congregation, title, username, password);
    }

    @GetMapping("/userDeletePost/{congregation}/{title}/{username}/{password}")
    public String userDeletePost(@PathVariable String congregation, @PathVariable String title, @PathVariable String username, @PathVariable String password) {
        return postService.deletePost(congregation, title, username, password);
    }
}