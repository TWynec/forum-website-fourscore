package com.fourumscore.forum.congregations;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "congregations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Congregation {

    @Id
    @Column(name = "title")
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
}