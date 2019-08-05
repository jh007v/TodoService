package com.example.spring.todo.controller;

import com.example.spring.todo.entity.User;
import com.example.spring.todo.service.LocalUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.Map;

@RestController
@RequestMapping("/signin")
public class SignInController {

    @Autowired
    private LocalUserDetailsService userDetailsService;

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            consumes = {
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE
            })
    public UserDetails signIn(@RequestBody Map<String, String> credential) {
        String username = credential.get("username");
        String password = credential.get("password");
        if (username == null || username.isEmpty()) {
            throw new InvalidParameterException("Username is NULL or EMPTY");
        } else if (password == null || password.isEmpty()) {
            throw new InvalidParameterException("Password is NULL or EMPTY");
        }

        User user = (User) userDetailsService.loadUserByUsername(username);
        if (user != null && password.compareTo(user.getPassword()) != 0) {
            throw new BadCredentialsException("Password does not match");
        }
        return user;
    }
}
