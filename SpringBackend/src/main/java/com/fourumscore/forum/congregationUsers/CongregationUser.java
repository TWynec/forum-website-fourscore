package com.fourumscore.forum.congregationUsers;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "congregation_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CongregationUser {

    @EmbeddedId
    private CongregationUserId congregationUserId;
    @Column(name = "posts")
    private int posts;
}