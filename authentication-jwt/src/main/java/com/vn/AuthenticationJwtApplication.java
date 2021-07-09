package com.vn;

import com.vn.entity.Erole;
import com.vn.entity.Role;
import com.vn.entity.User;
import com.vn.reposiroty.RoleRepository;
import com.vn.reposiroty.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@SpringBootApplication
@Slf4j
public class AuthenticationJwtApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationJwtApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0 && roleRepository.count() == 0) {
            Role role = new Role();
            role.setRoleName(Erole.ROLE_ADMIN);
            User user = new User();
            user.setEmail("anh@gmail.com");
            user.setUsername("nguyen van a");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRoles(Collections.singletonList(role));
            log.info("{}", userRepository.save(user));
        }
    }
}
