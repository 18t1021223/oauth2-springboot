package com.vn.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "oauth_refresh_token ")
@Entity
public class OauthRefreshToken {
    @Id
    @Column(name = "token_id")
    private String token_id;

    @Lob
    // @Basic(fetch = FetchType.LAZY)
    @Column(length = Integer.MAX_VALUE)
    private byte[] token;

    @Column(name = "authentication_id")
    private String authentication_id;
}
