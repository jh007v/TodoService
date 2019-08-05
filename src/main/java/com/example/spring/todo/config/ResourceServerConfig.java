package com.example.spring.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String CLIENT_ID = "762f6bbb-a257-11e9-9b39-0242ac120002";
    private static final String CLIENT_SECRET = "c16b2a8b36678a7440caeda356534ef2fa75699098bb7d58d499541024e53a51";
    private static final String ENDPOINT_URL = "http://localhost:8080/oauth/check_token";
    private static final String RESOURCE_ID = "todo-server-rest-api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId(RESOURCE_ID)
                .tokenServices(tokenServices());
    }

    @Bean
    public RemoteTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(CLIENT_ID);
        tokenServices.setClientSecret(CLIENT_SECRET);
        tokenServices.setCheckTokenEndpointUrl(ENDPOINT_URL);
        return tokenServices;
    }
}
