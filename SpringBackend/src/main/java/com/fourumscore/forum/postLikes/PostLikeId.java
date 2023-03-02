package com.fourumscore.forum.postLikes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Table(name = "post_likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeId implements Serializable {

    @Column(name = "congregation", nullable = false)
    private String congregation;
    @Column(name = "post_title", nullable = false)
    private String postTitle;
    @Column(name = "user", nullable = false)
    private String user;

}