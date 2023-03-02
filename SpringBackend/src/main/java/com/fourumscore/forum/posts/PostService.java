package com.fourumscore.forum.posts;

import com.fourumscore.forum.congregationUsers.CongregationUserService;
import com.fourumscore.forum.congregations.CongregationService;
import com.fourumscore.forum.postLikes.PostLikeService;
import com.fourumscore.forum.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CongregationService congregationService;
    @Autowired
    private UserService userService;
    @Autowired
    private CongregationUserService congregationUserService;
    @Autowired
    private PostLikeService postLikeService;

    public List<Post> postsInCongregation(String congregation) {
        return postRepo.findAllByPostIdCongregation(congregation);
    }

    public Optional<Post> post(String congregation, String title) {
        return postRepo.findById(new PostId(congregation, title));
    }

    public String createPost(String congregation, String title, String author, String password, String post) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (post(congregation, title).isEmpty()) {
                if (userService.user(author).isPresent()) {
                    if (userService.user(author).get().getPassword().equals(password)) {
                        postRepo.save(new Post(congregation, title, author, post));
                        congregationUserService.addCongregationUser(congregation, author);
                        return "Post created!";
                    }
                    return "Incorrect password.";
                }
                return author + " does not exist.";
            }
            return title + " already exists in " + congregation + ".";
        }
        return congregation + " does not exist.";
    }

    public String editTitle(String congregation, String username, String password, String oldTitle, String newTitle) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (userService.user(username).isPresent()) {
                if (userService.user(username).get().getPassword().equals(password)) {
                    if (!oldTitle.equals(newTitle)) {
                        if (post(congregation, oldTitle).isPresent()) {
                            if (username.equals("admin") || username.equals(post(congregation, oldTitle).get().getAuthor())) {
                                postRepo.updateTitle(newTitle, oldTitle);
                                return oldTitle + " is now " + newTitle + "!";
                            }
                            return "You do not have permission to edit this post.";
                        }
                        return oldTitle + " does not exist in " + congregation + ".";
                    }
                    return "Old title and new title cannot be the same.";
                }
                return "Incorrect password.";
            }
            return username + " does not exist.";
        }
        return congregation + " does not exist.";
    }

    public String editPost(String congregation, String username, String password, String title, String newPost) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (userService.user(username).isPresent()) {
                if (userService.user(username).get().getPassword().equals(password)) {
                    if (post(congregation, title).isPresent()) {
                        Post post = post(congregation, title).get();
                        if (!newPost.equals(post.getPost())) {
                            if (username.equals("admin") || username.equals(post.getAuthor())) {
                                postRepo.save(new Post(post.getPostId(), post.getAuthor(), newPost, post.getLikes(), post.getDislikes()));
                                return "Post updated!";
                            }
                            return "You do not have permission to edit this post.";
                        }
                        return "Old post and new post cannot be the same.";
                    }
                    return title + " does not exist in " + congregation + ".";
                }
                return "Incorrect password.";
            }
            return username + " does not exist.";
        }
        return congregation + " does not exist.";
    }

    public String deletePost(String congregation, String title, String username, String password) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (post(congregation, title).isPresent()) {
                if (userService.user(username).isPresent()) {
                    if (userService.user(username).get().getPassword().equals(password)) {
                        if (post(congregation, title).get().getAuthor().equals(username) || username.equals("admin")) {
                            postRepo.deleteById(new PostId(congregation, title));
                            return "Post deleted.";
                        }
                        return "You do not have permission to delete this post.";
                    }
                    return "Incorrect password.";
                }
                return username + " does not exist.";
            }
            return title + " does not exist in " + congregation + ".";
        }
        return congregation + " does not exist.";
    }

    public String likePost(String congregation, String title, String username, String password) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (post(congregation, title).isPresent()) {
                if (userService.user(username).isPresent()) {
                    if (userService.user(username).get().getPassword().equals(password)) {
                        int state = postLikeService.postLike(congregation, title, username);
                        Post post = post(congregation, title).get();
                        switch (state) {
                            case -1 -> {
                                postRepo.save(new Post(new PostId(congregation, title), post.getAuthor(), post.getPost(), post.getLikes() + 1, post.getDislikes() - 1));
                                return title + " switched from disliked to liked by " + username + ".";
                            }
                            case 0 -> {
                                postRepo.save(new Post(new PostId(congregation, title), post.getAuthor(), post.getPost(), post.getLikes() + 1, post.getDislikes()));
                                return title + " liked by " + username + ".";
                            }
                            case 1 -> {
                                postRepo.save(new Post(new PostId(congregation, title), post.getAuthor(), post.getPost(), post.getLikes() - 1, post.getDislikes()));
                                return title + " unliked by " + username + ".";
                            }
                        }
                    }
                    return "Incorrect password.";
                }
                return username + " does not exist.";
            }
            return title + " does not exist in " + congregation + ".";
        }
        return congregation + " does not exist.";
    }

    public String dislikePost(String congregation, String title, String username, String password) {
        if (congregationService.congregation(congregation).isPresent()) {
            if (post(congregation, title).isPresent()) {
                if (userService.user(username).isPresent()) {
                    if (userService.user(username).get().getPassword().equals(password)) {
                        int state = postLikeService.postDislike(congregation, title, username);
                        Post post = post(congregation, title).get();
                        switch (state) {
                            case -1 -> {
                                postRepo.save(new Post(new PostId(congregation, title), post.getAuthor(), post.getPost(), post.getLikes(), post.getDislikes() - 1));
                                return title + " undisliked by " + username + ".";
                            }
                            case 0 -> {
                                postRepo.save(new Post(new PostId(congregation, title), post.getAuthor(), post.getPost(), post.getLikes(), post.getDislikes() + 1));
                                return title + " disliked by " + username + ".";
                            }
                            case 1 -> {
                                postRepo.save(new Post(new PostId(congregation, title), post.getAuthor(), post.getPost(), post.getLikes() - 1, post.getDislikes() + 1));
                                return title + " switched from liked to disliked by " + username + ".";
                            }
                        }
                    }
                    return "Incorrect password.";
                }
                return username + " does not exist.";
            }
            return title + " does not exist in " + congregation + ".";
        }
        return congregation + " does not exist.";
    }
}