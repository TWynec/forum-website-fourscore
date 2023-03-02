package com.fourumscore.forum.posts;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @EmbeddedId
    private PostId postId;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "post", nullable = false)
    private String post;
    @Column(name = "likes")
    private int likes;
    @Column(name = "dislikes")
    private int dislikes;

    public Post(String congregation, String title, String author, String post) {
        this.postId = new PostId(congregation, title);
        this.author = author;
        this.post = post;
        this.likes = 0;
        this.dislikes = 0;
    }
}