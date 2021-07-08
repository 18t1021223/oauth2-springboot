package com.vn.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(length = 50,nullable = false)
    private String username;

    @Column(length = 50)
    private String email;

    @Column()
    private String password;

    @ManyToMany(fetch = FetchType.LAZY ,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "roleId")
    private List<Role> roles;

}
