package com.vn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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

    // mappedBy trỏ tới tên biến roles ở trong user.
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // ignore field
    private List<User> users;
}
