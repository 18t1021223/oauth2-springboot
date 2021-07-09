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
    /**
     * dùng @Transactional. Đê khiến toàn bộ code chạy trong hàm đều nằm trong Session quản lý của Hibernate.
     *
     * Nếu không có @Transactional thì việc bạn gọi user.getRoles() sẽ bị lỗi,
     * vì nó không thể query xuống database để lấy dữ liệu roles lên được
     */
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
