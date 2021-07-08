package com.vn.service;

import com.vn.entity.RefreshToken;
import com.vn.entity.User;
import com.vn.payloads.request.RefreshTokenRequest;
import com.vn.reposiroty.RefreshTokenRepository;
import com.vn.reposiroty.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class TokenProvider {
    public static final long EXPIRATIONTIME = 24 * 60 * 60 * 1000; // 1 days
    public static final long EXPIRATIONTIME_REFRESH_TOKEN = EXPIRATIONTIME * 2;
    public static final String SECRET = "ThisIsASecret";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public String generatedJwt(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        // check token user exists
        RefreshToken token = refreshTokenRepository.findByUser_UserId(user.getUserId());
        if (token == null) {
            token = new RefreshToken();
            token.setUser(user);
        }
        token.setExpiryDate(Instant.now().plusMillis(EXPIRATIONTIME_REFRESH_TOKEN));
        token.setToken(UUID.randomUUID().toString());
        refreshTokenRepository.save(token);
        return token;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteByUserId(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    public RefreshToken findRefreshToken(RefreshTokenRequest request) {
        RefreshToken token = refreshTokenRepository.findByToken(request.getToken());
        if (token == null)
            throw new RuntimeException("RefreshToken not exists");
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            this.deleteByUserId(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

}
