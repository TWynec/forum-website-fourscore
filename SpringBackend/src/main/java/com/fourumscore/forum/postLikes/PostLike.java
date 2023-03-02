package com.fourumscore.forum.postLikes;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {

    @EmbeddedId
    private PostLikeId postLikeId;
    @Column(name = "liked")
    private boolean liked;
    @Column(name = "disliked")
    private boolean disliked;
}