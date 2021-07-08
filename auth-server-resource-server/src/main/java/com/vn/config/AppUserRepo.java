package com.vn.service;

import com.vn.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser,String> {
}
