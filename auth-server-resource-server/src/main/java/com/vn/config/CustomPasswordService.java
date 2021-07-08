package com.vn.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

@Service
public class CustomPasswordService implements UserDetailsPasswordService {
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
            return null;
    }
}
