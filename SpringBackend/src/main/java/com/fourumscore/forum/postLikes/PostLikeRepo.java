package com.fourumscore.forum.postLikes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepo extends CrudRepository<PostLike, PostLikeId> {
}