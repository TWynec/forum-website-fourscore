package com.fourumscore.forum.congregations;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface CongregationRepo extends CrudRepository<Congregation, String> {
    @Modifying
    @Query("UPDATE Congregation c SET c.title = :newTitle WHERE c.title LIKE :oldTitle")
    void updateTitle(@Param("newTitle") String newTitle, @Param("oldTitle") String oldTitle);
}