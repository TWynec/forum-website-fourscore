package com.fourumscore.forum.posts;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface PostRepo extends CrudRepository<Post, PostId> {
    List<Post> findAllByPostIdCongregation(String congregation);

    @Modifying
    @Query("UPDATE Post p SET p.postId.title = :newTitle WHERE p.postId.title LIKE :oldTitle")
    void updateTitle(@Param("newTitle") String newTitle, @Param("oldTitle") String oldTitle);
}