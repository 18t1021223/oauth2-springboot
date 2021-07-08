package com.vn.reposiroty;

import com.vn.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    RefreshToken findByToken(String token);
    RefreshToken findByUser_UserId(int userId);
}
