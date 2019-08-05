package com.example.spring.todo.controller;

import com.example.spring.todo.entity.User;
import com.example.spring.todo.model.ResultItems;
import com.example.spring.todo.service.LocalUserDetailsService;
import com.example.spring.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private LocalUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(
            path = "/me",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public User me(Principal principal) {
        return (User) userDetailsService.loadUserByUsername(principal.getName());
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public User create(@RequestBody User user) {
        return null;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public ResultItems<User> listOf() {
        return null;
    }

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public User retrieve(@PathVariable("id") String id) {
        return null;
    }

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.PATCH,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public User update(@PathVariable("id") String id, @RequestBody User user) {
        return null;
    }

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.DELETE,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public void delete(@PathVariable("id") String id) {
    }
}
