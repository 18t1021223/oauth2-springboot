package com.vn.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "oauth_client_token")
@Entity
public class OauthClientToken {
    @Column(name = "token_id")
    private String token_id;

    /*
        0 < length <=      255  -->  `TINYBLOB`
         255 < length <=    65535  -->  `BLOB`
       65535 < length <= 16777215  -->  `MEDIUMBLOB`
    16777215 < length <=    2³¹-1  -->  `LONGBLOB`
     */
    @Lob
    // @Basic(fetch = FetchType.LAZY)
    @Column(length = Integer.MAX_VALUE)
    private byte[] token;

    @Id
    @Column(name = "authentication_id")
    private String authentication_id;

    private String userName;
    private String clientId;
}
