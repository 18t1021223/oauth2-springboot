package com.vn;

import com.vn.entity.AppUser;
import com.vn.entity.OAuthClientDetail;
import com.vn.entity.Post;
import com.vn.service.AppUserRepo;
import com.vn.service.OauthClientDetailsRepo;
import com.vn.service.PostRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Log4j2
public class AuthServerResourceServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerResourceServerApplication.class, args);
    }

    @Autowired
    private PostRepo postRepository;
    @Autowired
    private AppUserRepo appUserRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private OauthClientDetailsRepo clientDetailsRepo;

    @Override
    public void run(String... args) throws Exception {
        postRepository.save(new Post(1, "Post title nr1", "Post content nr1", null));
        postRepository.save(new Post(2, "Post title nr2", "Post content nr2", null));
        postRepository.save(new Post(3, "Post title nr3", "Post content nr3", null));
        log.info("save :" + postRepository.count());
        appUserRepo.save(new AppUser("sona", passwordEncoder.encode("123"), "USER"));
        log.info("save :user");

        OAuthClientDetail o = OAuthClientDetail.builder()
                .clientId("client_id")
                .clientSecret(passwordEncoder.encode("client_pass"))
                .accessTokenValidity(7200)
                .scope("write,read")
                .authorizedGrantTypes("password,authorization_code,refresh_token")
                .build();

        clientDetailsRepo.save(o);
    }
}
