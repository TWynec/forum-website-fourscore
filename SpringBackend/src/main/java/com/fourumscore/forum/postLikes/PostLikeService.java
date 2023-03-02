package com.fourumscore.forum.postLikes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeRepo postLikeRepo;

    public int userLiked(String congregation, String postTitle, String user) {
        Optional<PostLike> userLike = postLikeRepo.findById(new PostLikeId(congregation, postTitle, user));
        if (userLike.isPresent()) {
            if (userLike.get().isLiked()) return 1;
            return -1;
        }
        return 0;
    }

    public int postLike(String congregation, String postTitle, String user) {
        PostLikeId postLikeId = new PostLikeId(congregation, postTitle, user);
        int state = userLiked(congregation, postTitle, user);
        if (state == 1) postLikeRepo.deleteById(postLikeId);
        else postLikeRepo.save(new PostLike(postLikeId, true, false));
        return state;
    }

    public int postDislike(String congregation, String postTitle, String user) {
        PostLikeId postLikeId = new PostLikeId(congregation, postTitle, user);
        int state = userLiked(congregation, postTitle, user);
        if (state == -1) postLikeRepo.deleteById(postLikeId);
        else postLikeRepo.save(new PostLike(postLikeId, false, true));
        return state;
    }
}
