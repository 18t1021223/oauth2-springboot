package com.vn.configurations;

import com.vn.entity.Erole;
import com.vn.reposiroty.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

@Slf4j
public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final RoleRepository roleRepository;


    public CustomMethodSecurityExpressionRoot(Authentication authentication, RoleRepository roleRepository) {
        super(authentication);
        this.roleRepository = roleRepository;
    }

    @Override
    public void setFilterObject(Object filterObject) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }

    public boolean customIsMember(String roleRequest) {
        if (roleRequest == null || roleRequest.isEmpty()) return false;
        try {
            Erole e;
            switch (roleRequest) {
                case "ADMIN":
                    e = Erole.ROLE_ADMIN;
                    break;
                case "MODERATOR":
                    e = Erole.ROLE_MODERATOR;
                    break;
                case "USER":
                    e = Erole.ROLE_USER;
                    break;
                default:
                    return false;
            }
            return roleRepository.
                    findByRoleNameAndUsers_Username(e, this.authentication.getName()) != null;
        } catch (Exception exception) {
            log.error("cannot check role {}", exception);
            return false;
        }
    }

//    @Override
//    public void setDefaultRolePrefix(String defaultRolePrefix) {
//        super.setDefaultRolePrefix(defaultRolePrefix);
//    }


}
