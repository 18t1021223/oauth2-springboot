package com.vn.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @NotEmpty(message = "username not null")
    private String username;

    @NotEmpty(message = "password not null")
    private String password;
}
