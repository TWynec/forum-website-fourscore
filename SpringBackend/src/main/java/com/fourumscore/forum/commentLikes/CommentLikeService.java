package com.fourumscore.forum.commentLikes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepo commentLikeRepo;

    public int userLiked(String congregation, String postTitle, String commentTitle, String user) {
        Optional<CommentLike> userLike = commentLikeRepo.findById(new CommentLikeId(congregation, postTitle, commentTitle, user));
        if (userLike.isPresent()) {
            if (userLike.get().isLiked()) return 1;
            return -1;
        }
        return 0;
    }

    public int commentLike(String congregation, String postTitle, String commentTitle, String user) {
        CommentLikeId commentLikeId = new CommentLikeId(congregation, postTitle, commentTitle, user);
        int state = userLiked(congregation, postTitle, commentTitle, user);
        if (state == 1) commentLikeRepo.deleteById(commentLikeId);
        else commentLikeRepo.save(new CommentLike(commentLikeId, true, false));
        return state;
    }

    public int commentDislike(String congregation, String postTitle, String commentTitle, String user) {
        CommentLikeId commentLikeId = new CommentLikeId(congregation, postTitle, commentTitle, user);
        int state = userLiked(congregation, postTitle, commentTitle, user);
        if (state == -1) commentLikeRepo.deleteById(commentLikeId);
        else commentLikeRepo.save(new CommentLike(commentLikeId, false, true));
        return state;
    }
}
