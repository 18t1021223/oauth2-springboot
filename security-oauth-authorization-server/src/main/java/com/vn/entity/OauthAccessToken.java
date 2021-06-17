package com.vn.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "oauth_access_token")
@Entity
public class OauthAccessToken {
    private String tokenId;

    @Lob
    // @Basic(fetch = FetchType.LAZY)
    @Column(length = Integer.MAX_VALUE)
    private byte[] token;

    @Id
    @Column(name = "authentication_id")
    private String authentication_id;

    private String userName;

    private String clientId;

    @Lob
    // @Basic(fetch = FetchType.LAZY)
    @Column(length = Integer.MAX_VALUE)
    private byte[] authentication;

    private String refreshToken;
}
