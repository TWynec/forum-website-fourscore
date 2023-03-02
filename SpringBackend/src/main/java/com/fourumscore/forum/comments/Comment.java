package com.fourumscore.forum.comments;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @EmbeddedId
    CommentId commentId;
    @Column(name = "comment_author", nullable = false)
    private String author;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "likes")
    private int likes;
    @Column(name = "dislikes")
    private int dislikes;

    public Comment(String congregation, String postTitle, String commentTitle, String author, String comment) {
        this.commentId = new CommentId(congregation, postTitle, commentTitle);
        this.author = author;
        this.comment = comment;
        this.likes = 0;
        this.dislikes = 0;
    }
}