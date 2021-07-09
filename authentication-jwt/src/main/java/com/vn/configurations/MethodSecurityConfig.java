package com.vn.configurations;

import com.vn.reposiroty.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
//The @EnableGlobalMethodSecurity permits to specify security on the method level.
// Its attribute proxyTargetClass is set in order to have this working for RestController’s methods,
// because controllers are usually classes, not implementing any interfaces.
public class MethodSecurityConfig
        extends GlobalMethodSecurityConfiguration {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        CustomMethodSecurityExpressionHandler expressionHandler =
                new CustomMethodSecurityExpressionHandler(roleRepository);
        expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return expressionHandler;
    }
}
