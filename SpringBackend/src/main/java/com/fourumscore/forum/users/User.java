package com.fourumscore.forum.users;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "bio")
    private String bio;
    @Column(name = "pfp")
    private String pfp;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = null;
        this.pfp = null;
    }
}