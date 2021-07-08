package com.vn.payloads.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotBlank(message = "RefreshToken not null")
    private String token;
}
