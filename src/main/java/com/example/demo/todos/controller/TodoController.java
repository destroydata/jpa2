package com.example.demo.todos.controller;

import com.example.demo.config.aspect.TokenRequired;
import com.example.demo.config.auth.AuthService;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService todoService;
    private final AuthService authService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @TokenRequired
    public void postTodos(
            @RequestBody TodoRequest request,
            @RequestHeader("Authorization") String token
    ){
        Map<String, Object> map = authService
                .getClaims(token.replace("Bearer ", ""));
        request.setMemberId(((Integer) map.get("memberId")).longValue());
        todoService.insert(request);
    }
    @PutMapping("{todoId}/check")
    @TokenRequired
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void check(
            @PathVariable("todoId") Long todoId,
            @RequestHeader("Authorization") String token
            ){
        Map<String, Object> map =
                authService.getClaims(token.replace("Bearer ", ""));
        Long memberId = ((Integer) map.get("memberId")).longValue();
        todoService.check(todoId, memberId);
    }
}
