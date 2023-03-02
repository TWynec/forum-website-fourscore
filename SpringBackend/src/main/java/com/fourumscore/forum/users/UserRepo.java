package com.fourumscore.forum.users;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepo extends CrudRepository<User, String> {
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.username = :newUsername WHERE u.username LIKE :oldUsername")
    void updateUsername(@Param("newUsername") String newUsername, @Param("oldUsername") String oldUsername);

    Optional<User> findByUsernameAndPassword(String username, String password);
}