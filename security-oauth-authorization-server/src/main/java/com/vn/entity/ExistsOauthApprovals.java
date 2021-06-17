package com.vn.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "exists_oauth_approvals")
@Entity
public class ExistsOauthApprovals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userId;
    private String clientId;
    private String scope;

    @Column(length = 10)
    private String status;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expiresAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastModifiedAt;
}
