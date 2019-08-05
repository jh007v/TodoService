package com.example.spring.todo.service;

import com.example.spring.todo.entity.Role;
import com.example.spring.todo.entity.User;
import com.example.spring.todo.model.Signup;
import com.example.spring.todo.repository.RoleRepository;
import com.example.spring.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SignupService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User registerUser(User user) {
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(Stream.of(role).collect(Collectors.toSet()));
        return userRepository.save(user);
    }
}
