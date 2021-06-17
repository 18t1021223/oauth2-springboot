package com.vn.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "oauth_client_details")
@Entity
public class OauthClientDetails {
    @Id
    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    @Column(name = "authorized_grant_types")
    private String authorized_grant_types;

    @Column(name = "web_serve_redirect_uri")
    private String web_serve_redirect_uri;

    private String authorities;

    @Column(name = "access_token_validity")
    private int access_token_validity;

    @Column(name = "refresh_token_validity ")
    private int refresh_token_validity;

    @Column(name = "additional_information", length = 4096)
    private String additional_information;

    private boolean autoApprove;
}
