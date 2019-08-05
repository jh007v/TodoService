package com.example.spring.todo.service;

import com.example.spring.todo.entity.Todo;
import com.example.spring.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Transactional
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Transactional(readOnly = true)
    public Page<Todo> listOfTodo(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Todo> itemOfTodo(String id) {
        return todoRepository.findById(id);
    }

    @Transactional
    public Todo updateTodo(String id, Todo todo) {
        return todoRepository.save(todo);
    }

    @Transactional
    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }
}
