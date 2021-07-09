package com.vn.reposiroty;

import com.vn.entity.Erole;
import com.vn.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleNameAndUsers_Username(Erole roleName, String username);
}
