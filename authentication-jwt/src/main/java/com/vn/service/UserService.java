package com.vn.service;

import com.vn.Utils;
import com.vn.entity.Erole;
import com.vn.entity.Role;
import com.vn.entity.User;
import com.vn.payloads.request.SignupRequest;
import com.vn.reposiroty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username exists");
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email exists");

        User user = new User();

        List<Role> roles = new ArrayList<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            request.getRoles().forEach(value -> {
                Role role = new Role();
                switch (value.toLowerCase()) {
                    case "admin":
                        role.setRoleName(Erole.ROLE_ADMIN);
                        break;
                    case "user":
                        role.setRoleName(Erole.ROLE_USER);
                        break;
                    case "moderator":
                        role.setRoleName(Erole.ROLE_MODERATOR);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + value.toLowerCase());
                }
                roles.add(role);
            });
        } else {
            Role role = new Role();
            role.setRoleName(Erole.ROLE_USER);
            roles.add(role);
        }
        user.setRoles(roles);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public List<Object> findAll() throws IllegalAccessException {
        String excludes[] = new String[]{"password"};

        List<Object> objects;
        objects = userRepository.findAll()
                .stream()
                .map(value -> {
                    try {
                        return Utils.buildExcludes(value, excludes);
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                }).collect(Collectors.toList());
        return objects;
    }
}
