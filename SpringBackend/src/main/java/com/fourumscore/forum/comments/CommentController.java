package com.fourumscore.forum.comments;

import com.fourumscore.forum.congregations.CongregationService;
import com.fourumscore.forum.posts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CongregationService congregationService;
    @Autowired
    private PostService postService;

    @GetMapping("/{congregation}/{post}")
    public String[][] getComments(@PathVariable String congregation, @PathVariable String post) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (postService.post(congregation, post).isPresent()) {
                List<Comment> comments = commentService.commentsInPost(congregation, post);
                if (comments.size() > 0) {
                    String[][] commentsArr = new String[comments.size()][];
                    for (int i = 0; i < comments.size(); i++) {
                        Comment present = comments.get(i);
                        commentsArr[i] = new String[] { present.getCommentId().getCongregation(), present.getCommentId().getPostTitle(), present.getCommentId().getCommentTitle(), present.getAuthor(), present.getComment(), String.valueOf(present.getLikes()), String.valueOf(present.getDislikes()) };
                    }
                    return commentsArr;
                }
                else return new String[][] { { "There are no comments on " + post + ". Be the first to make one!" } };
            }
            else return new String[][] { { post + " does not exist. But you can make it ;)" } };
        }
        else return new String[][] { { congregation + " does not exist. But you can make it ;)" } };
    }

    @GetMapping("/createNewComment/{congregation}/{postTitle}/{commentTitle}/{author}/{password}/{comment}")
    public String createNewComment(@PathVariable String congregation, @PathVariable String postTitle, @PathVariable String commentTitle, @PathVariable String author, @PathVariable String password, @PathVariable String comment) {
        return commentService.createComment(congregation, postTitle, commentTitle, author, password, comment);
    }

    @GetMapping("/editCommentTitle/{congregation}/{postTitle}/{username}/{password}/{oldTitle}/{newTitle}")
    public String editCommentTitle(@PathVariable String congregation, @PathVariable String postTitle, @PathVariable String username, @PathVariable String password, @PathVariable String oldTitle, @PathVariable String newTitle) {
        return commentService.editTitle(congregation, postTitle, username, password, oldTitle, newTitle);
    }

    @GetMapping("/editCommentBody/{congregation}/{postTitle}/{commentTitle}/{username}/{password}/{newComment}")
    public String editCommentBody(@PathVariable String congregation, @PathVariable String postTitle, @PathVariable String commentTitle, @PathVariable String username, @PathVariable String password, @PathVariable String newComment) {
        return commentService.editComment(congregation, postTitle, commentTitle, username, password, newComment);
    }

    @GetMapping("/userDeleteComment/{congregation}/{postTitle}/{commentTitle}/{username}/{password}")
    public String userDeleteComment(@PathVariable String congregation, @PathVariable String postTitle, @PathVariable String commentTitle, @PathVariable String username, @PathVariable String password) {
        return commentService.deleteComment(congregation, postTitle, commentTitle, username, password);
    }

    @GetMapping("/userLikeComment/{congregation}/{postTitle}/{commentTitle}/{username}/{password}")
    public String userLikeComment(@PathVariable String congregation, @PathVariable String postTitle, @PathVariable String commentTitle, @PathVariable String username, @PathVariable String password) {
        return commentService.likeComment(congregation, postTitle, commentTitle, username, password);
    }

    @GetMapping("/userDislikeComment/{congregation}/{postTitle}/{commentTitle}/{username}/{password}")
    public String userDislikeComment(@PathVariable String congregation, @PathVariable String postTitle, @PathVariable String commentTitle, @PathVariable String username, @PathVariable String password) {
        return commentService.dislikeComment(congregation, postTitle, commentTitle, username, password);
    }
}