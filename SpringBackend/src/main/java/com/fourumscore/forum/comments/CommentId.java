package com.fourumscore.forum.comments;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Table(name = "post_comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentId implements Serializable {

    @Column(name = "congregation", nullable = false)
    private String congregation;
    @Column(name = "post_title", nullable = false)
    private String postTitle;
    @Column(name = "comment_title", nullable = false)
    private String commentTitle;
}