package com.vn.service;

import com.vn.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DefaultUserService implements UserDetailsService {
    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserFactory(appUserRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found ")));
    }

    private User UserFactory(AppUser u) {
        return new User(u.getUsername(),
                u.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(u.getRoles())));
    }
}
