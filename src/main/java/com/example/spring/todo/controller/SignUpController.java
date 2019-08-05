package com.example.spring.todo.controller;

import com.example.spring.todo.entity.User;
import com.example.spring.todo.model.Signup;
import com.example.spring.todo.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private SignupService signupService;

    @PreAuthorize("hasAnyRole('GUEST')")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public User signup(@RequestBody @Valid User user) {
        return signupService.registerUser(user);
    }
}
