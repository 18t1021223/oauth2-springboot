package com.vn.controller;

import com.vn.Utils;
import com.vn.configurations.TokenProvider;
import com.vn.payloads.request.LoginRequest;
import com.vn.payloads.request.SignupRequest;
import com.vn.payloads.response.JwtResponse;
import com.vn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserService userService;


    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest request, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.ok(
                    Utils.buildResponse(
                            99,
                            "username , password not null or empty",
                            null,
                            result.getFieldErrors()
                    )
            );
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(Utils.buildResponse(
                0,
                null,
                new JwtResponse(
                        tokenProvider.generatedJwt(user.getUsername()),
                        "Bearer",
                        user.getUsername(),
                        user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                ),
                null
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.ok(
                    Utils.buildResponse(
                            99,
                            "Info not null or empty",
                            null,
                            result.getFieldErrors()
                    )
            );

        try {
            userService.signup(request);
            return ResponseEntity.ok(Utils.buildResponse(0, null, null, null));
        } catch (Exception exception) {
            return ResponseEntity.ok(Utils.buildResponse(1, exception.getMessage(), null, null));
        }
    }
}
