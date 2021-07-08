package com.vn.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "role")
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Erole roleName;

}
