package com.example.demo.todos.repository;

import com.example.demo.todos.domain.dto.TodoCondition;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.domain.response.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CustomTodoRepository {
    Page<TodoResponse> findAllByCondition(
            PageRequest request,
            TodoCondition condition
    );
}
