package com.example.spring.todo.config;

import com.google.common.net.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final static String AUTH_SERVER = "http://localhost:8080/oauth";
    private final static String CLIENT_ID = "762f6bbb-a257-11e9-9b39-0242ac120002";
    private final static String CLIENT_SECRET = "c16b2a8b36678a7440caeda356534ef2fa75699098bb7d58d499541024e53a51";

    private String accessTokenUri ="http://localhost:8080/oauth/token";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.spring.todo.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(securitySchema(), apiKey(), apiCookieKey()))
                ;//.globalResponseMessage(RequestMethod.GET, Arrays.asList(responseMessages()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("To-Do REST API")
                .description("To-Do Management Service")
                .contact(new Contact("Steve Park", "www.bitcamp.co.kr", "steve.park@bitcamp.co.kr"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

    @Bean
    public SecurityScheme apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, "apiKey", "header");
    }

    @Bean
    public SecurityScheme apiCookieKey() {
        return new ApiKey(HttpHeaders.COOKIE, "apiKey", "cookie");
    }

    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
        grantTypes.add(passwordCredentialsGrant);

        return new OAuth("oauth2", authorizationScopeList, grantTypes);
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauth2"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_ID))
                .build();

        SecurityScheme oauth = new OAuthBuilder().name("oauth2")
                .grantTypes(Arrays.asList(grantType))
                .scopes(Arrays.asList(authorizationScopes()))
                .build();
        return oauth;
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .appName("todo-server-rest-api")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("/*"))
                .build();
    }

    private List<SecurityReference> securityReferences() {
        return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes()));
    }

    private AuthorizationScope[] authorizationScopes() {
        AuthorizationScope[] authorizationScopes = {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations")
        };
        return authorizationScopes;
    }

    private ResponseMessage[] responseMessages() {
        ResponseMessage[] responseMessages = {
                new ResponseMessageBuilder()
                        .code(500)
                        .message("500 message")
                        .responseModel(new ModelRef("Error"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(403)
                        .message("Forbidden!!!!!")
                        .build()
        };
        return responseMessages;
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }
}
