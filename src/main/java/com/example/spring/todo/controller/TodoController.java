package com.example.spring.todo.controller;

import com.example.spring.todo.entity.Todo;
import com.example.spring.todo.entity.User;
import com.example.spring.todo.model.ResultItems;
import com.example.spring.todo.service.LocalUserDetailsService;
import com.example.spring.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private LocalUserDetailsService userDetailsService;

    @Autowired
    private TodoService todoService;

    @PreAuthorize("hasAnyAuthority('WRITE_TODO')")
    @RequestMapping(
            method = RequestMethod.POST,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public Todo create(
            Principal principal,
            @RequestBody Todo todo) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        todo.setUser(user);
        return todoService.createTodo(todo);
    }

    @PreAuthorize("hasAnyAuthority('READ_TODO')")
    @RequestMapping(
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public ResultItems<Todo> listOf(
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Todo> todoList = todoService.listOfTodo(pageable);
        return new ResultItems<Todo>(todoList.stream().collect(Collectors.toList()), page, size, todoList.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('READ_TODO')")
    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public Todo retrieve(@PathVariable("id") String id) {
        return todoService.itemOfTodo(id).get();
    }

    @PreAuthorize("hasAnyAuthority('WRITE_TODO')")
    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.PATCH,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public Todo update(@PathVariable("id") String id, @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    @PreAuthorize("hasAnyAuthority('WRITE_TODO')")
    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.DELETE,
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            }
    )
    public Todo delete(@PathVariable("id") String id) {
        todoService.deleteTodo(id);

        Todo todo = new Todo();
        todo.setId(id);
        return todo;
    }
}
