package com.vn.payloads.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SignupRequest {
    @NotBlank(message = "username not null")
    @Length(message = "length > 3")
    private String username;

    @Email(message = "email not correct")
    @NotBlank(message = "Email not null")
    private String email;

    @NotBlank(message = "password not null")
    @Length(message = "length >5")
    private String password;

    private List<String> roles;
}
