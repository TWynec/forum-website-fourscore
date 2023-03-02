package com.fourumscore.forum.commentLikes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepo extends CrudRepository<CommentLike, CommentLikeId> {
}