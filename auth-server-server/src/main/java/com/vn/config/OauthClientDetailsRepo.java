package com.vn.config;

import com.vn.entity.OAuthClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientDetailsRepo extends JpaRepository<OAuthClientDetail,String> {
}
