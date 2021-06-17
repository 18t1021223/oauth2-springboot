package com.vn.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private String content;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},mappedBy = "post")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments;
}
