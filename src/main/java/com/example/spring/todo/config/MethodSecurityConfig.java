package com.example.spring.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
        // @PreAuthorize, @PostAuthorize
        prePostEnabled = true,
        // @Secured
        securedEnabled = true,
        // @RolesAllowed
        jsr250Enabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}
