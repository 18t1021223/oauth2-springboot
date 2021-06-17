package com.vn.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "client_details ")
@Entity
public class ClientDetails {
    @Id
    private String appId;

    private String resourceIds;

    private String appSecret;

    private String scope;

    private String grantTypes;

    private String redirectUrl;

    private String authorities;

    private int access_token_validity;

    private int refresh_token_validity;

    @Column(length = 4096)
    private String additionalInformation;

    private String autoApproveScopes;
}
