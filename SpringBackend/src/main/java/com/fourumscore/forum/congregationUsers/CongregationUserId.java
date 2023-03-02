package com.fourumscore.forum.congregationUsers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Table(name = "congregation_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CongregationUserId implements Serializable {

    @Column(name = "congregation", nullable = false)
    private String congregation;
    @Column(name = "user", nullable = false)
    private String user;
}