package com.vn.configurations;

import com.vn.entity.User;
import com.vn.reposiroty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userRepository.findByUsernameIgnoreCase(username);
        } catch (Exception ex) {
            throw new RuntimeException("Database error");
        }
        if (user == null)
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        return userDetailsBuild(user);
    }

    public org.springframework.security.core.userdetails.User userDetailsBuild(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(value -> new SimpleGrantedAuthority(value.getRoleName().name()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}
