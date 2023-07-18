package com.example.demo.todos.service;

import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    public void insert(TodoRequest request){
        todoRepository.save(request.toEntity());
    }
}
