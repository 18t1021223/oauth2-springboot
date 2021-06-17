package com.vn.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "existsOauthCode")
@Entity
public class ExistsOauthCode {
    private String code ;

    @Id
    @Column(name = "authentication_id")
    private String authentication;
}
