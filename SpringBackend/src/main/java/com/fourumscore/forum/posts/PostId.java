package com.fourumscore.forum.posts;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostId implements Serializable {

    @Column(name = "congregation", nullable = false)
    private String congregation;
    @Column(name = "title", nullable = false)
    private String title;
}