package com.fourumscore.forum.comments;

import com.fourumscore.forum.commentLikes.CommentLikeService;
import com.fourumscore.forum.congregations.CongregationService;
import com.fourumscore.forum.posts.PostService;
import com.fourumscore.forum.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private CommentLikeService commentLikeService;
    @Autowired
    private CongregationService congregationService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    public List<Comment> commentsInPost(String congregation, String post) {
        return commentRepo.findAllByCommentIdCongregationAndCommentIdPostTitle(congregation, post);
    }
    
    public Optional<Comment> comment(String congregation, String postTitle, String commentTitle) {
        return commentRepo.findById(new CommentId(congregation, postTitle, commentTitle));
    }

    public String createComment(String congregation, String postTitle, String commentTitle, String author, String password, String comment) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (postService.post(congregation, postTitle).isPresent()) {
                if (comment(congregation, postTitle, commentTitle).isEmpty()) {
                    if (userService.user(author).isPresent()) {
                        if (userService.user(author).get().getPassword().equals(password)) {
                            commentRepo.save(new Comment(congregation, postTitle, commentTitle, author, comment));
                            return "Comment created!";
                        }
                        return "Incorrect password.";
                    }
                    return author + " does not exist.";
                }
                return commentTitle + " already exists in " + postTitle + " in " + congregation + ".";
            }
            return postTitle + " does not exist in " + congregation + ".";
        }
        return congregation + " does not exist.";
    }

    public String editTitle(String congregation, String postTitle, String username, String password, String oldTitle, String newTitle) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (userService.user(username).isPresent()) {
                if (userService.user(username).get().getPassword().equals(password)) {
                    if (postService.post(congregation, postTitle).isPresent()) {
                        if (!oldTitle.equals(newTitle)) {
                            if (comment(congregation, postTitle, oldTitle).isPresent()) {
                                if (username.equals("admin") || username.equals(comment(congregation, postTitle, oldTitle).get().getAuthor())) {
                                    commentRepo.updateTitle(newTitle, oldTitle);
                                    return oldTitle + " is now " + newTitle + "!";
                                }
                                return "You do not have permission to edit this post.";
                            }
                            return oldTitle + " does not exist in " + postTitle + " in " + congregation + ".";
                        }
                        return "Old title and new title cannot be the same.";
                    }
                    return postTitle + " does not exist in " + congregation + ".";
                }
                return "Incorrect password.";
            }
            return username + " does not exist.";
        }
        return congregation + " does not exist.";
    }

    public String editComment(String congregation, String postTitle, String commentTitle, String username, String password, String newComment) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (userService.user(username).isPresent()) {
                if (userService.user(username).get().getPassword().equals(password)) {
                    if (postService.post(congregation, postTitle).isPresent()) {
                        if (comment(congregation, postTitle, commentTitle).isPresent()) {
                            Comment comment = comment(congregation, postTitle, commentTitle).get();
                            if (!newComment.equals(comment.getComment())) {
                                if (username.equals("admin") || username.equals(comment.getAuthor())) {
                                    commentRepo.save(new Comment(comment.getCommentId(), comment.getAuthor(), newComment, comment.getLikes(), comment.getDislikes()));
                                    return "Comment updated!";
                                }
                                return "You do not have permission to edit this comment.";
                            }
                            return "Old comment and new comment cannot be the same.";
                        }
                        return commentTitle + " does not exist in " + postTitle + " in " + congregation + ".";
                    }
                    return postTitle + " does not exist in " + congregation + ".";
                }
                return "Incorrect password.";
            }
            return username + " does not exist.";
        }
        return congregation + " does not exist.";
    }

    public String deleteComment(String congregation, String postTitle, String commentTitle, String username, String password) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (postService.post(congregation, postTitle).isPresent()) {
                if (comment(congregation, postTitle, commentTitle).isPresent()) {
                    if (userService.user(username).isPresent()) {
                        if (userService.user(username).get().getPassword().equals(password)) {
                            if (comment(congregation, postTitle, commentTitle).get().getAuthor().equals(username) || username.equals("admin")) {
                                commentRepo.deleteById(new CommentId(congregation, postTitle, commentTitle));
                                return "Comment deleted.";
                            }
                            return "You do not have permission to delete this comment.";
                        }
                        return "Incorrect password.";
                    }
                    return username + " does not exist.";
                }
                return commentTitle + " does not exist in " + postTitle + " in " + congregation + ".";
            }
            return postTitle + " does not exist in " + congregation + ".";
        }
        return congregation + " does not exist.";
    }

    public String likeComment(String congregation, String postTitle, String commentTitle, String username, String password) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (postService.post(congregation, postTitle).isPresent()) {
                if (comment(congregation, postTitle, commentTitle).isPresent()) {
                    if (userService.user(username).isPresent()) {
                        if (userService.user(username).get().getPassword().equals(password)) {
                            int state = commentLikeService.commentLike(congregation, postTitle, commentTitle, username);
                            Comment comment = comment(congregation, postTitle, commentTitle).get();
                            switch (state) {
                                case -1 -> {
                                    commentRepo.save(new Comment(new CommentId(congregation, postTitle, commentTitle), comment.getAuthor(), comment.getComment(), comment.getLikes() + 1, comment.getDislikes() - 1));
                                    return commentTitle + " switched from disliked to liked by " + username + ".";
                                }
                                case 0 -> {
                                    commentRepo.save(new Comment(new CommentId(congregation, postTitle, commentTitle), comment.getAuthor(), comment.getComment(), comment.getLikes() + 1, comment.getDislikes()));
                                    return commentTitle + " liked by " + username + ".";
                                }
                                case 1 -> {
                                    commentRepo.save(new Comment(new CommentId(congregation, postTitle, commentTitle), comment.getAuthor(), comment.getComment(), comment.getLikes() - 1, comment.getDislikes()));
                                    return commentTitle + " unliked by " + username + ".";
                                }
                            }
                        }
                        return "Incorrect password.";
                    }
                    return username + " does not exist.";
                }
                return commentTitle + " does not exist in " + postTitle + " in " + congregation + ".";
            }
            return postTitle + " does not exist in " + congregation + ".";
        }
        return congregation + " does not exist.";
    }

    public String dislikeComment(String congregation, String postTitle, String commentTitle, String username, String password) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (postService.post(congregation, postTitle).isPresent()) {
                if (comment(congregation, postTitle, commentTitle).isPresent()) {
                    if (userService.user(username).isPresent()) {
                        if (userService.user(username).get().getPassword().equals(password)) {
                            int state = commentLikeService.commentDislike(congregation, postTitle, commentTitle, username);
                            Comment comment = comment(congregation, postTitle, commentTitle).get();
                            switch (state) {
                                case -1 -> {
                                    commentRepo.save(new Comment(new CommentId(congregation, postTitle, commentTitle), comment.getAuthor(), comment.getComment(), comment.getLikes(), comment.getDislikes() - 1));
                                    return commentTitle + " undisliked by " + username + ".";
                                }
                                case 0 -> {
                                    commentRepo.save(new Comment(new CommentId(congregation, postTitle, commentTitle), comment.getAuthor(), comment.getComment(), comment.getLikes(), comment.getDislikes() + 1));
                                    return commentTitle + " disliked by " + username + ".";
                                }
                                case 1 -> {
                                    commentRepo.save(new Comment(new CommentId(congregation, postTitle, commentTitle), comment.getAuthor(), comment.getComment(), comment.getLikes() - 1, comment.getDislikes() + 1));
                                    return commentTitle + " switched from liked to disliked by " + username + ".";
                                }
                            }
                        }
                        return "Incorrect password.";
                    }
                    return username + " does not exist.";
                }
                return commentTitle + " does not exist in " + postTitle + " in " + congregation + ".";
            }
            return postTitle + " does not exist in " + congregation + ".";
        }
        return congregation + " does not exist.";
    }
}
