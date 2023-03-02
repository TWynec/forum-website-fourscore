package com.fourumscore.forum.comments;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends CrudRepository<Comment, CommentId> {
    List<Comment> findAllByCommentIdCongregationAndCommentIdPostTitle(String congregation, String postTitle);

    @Modifying
    @Query("UPDATE Comment c SET c.commentId.commentTitle = :newTitle WHERE c.commentId.commentTitle LIKE :oldTitle")
    void updateTitle(@Param("newTitle") String newTitle, @Param("oldTitle") String oldTitle);
}