package com.fourumscore.forum.commentLikes;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLike {

    @EmbeddedId
    private CommentLikeId commentLikeId;
    @Column(name = "liked")
    private boolean liked;
    @Column(name = "disliked")
    private boolean disliked;
}