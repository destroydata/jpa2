package com.example.demo.todos.service;

import com.example.demo.config.service.MemberLoginService;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberLoginService memberLoginService;
    public void insert(TodoRequest request){
//        memberLoginService.findByMember(request.getMemberId());
        todoRepository.save(request.toEntity());
    }
//PUT {todoId}/check (isDone -> true)
//202
//404
//TODOS_NOT_FOUND
//401
//CHECK LOGIN USRE
    @Transactional
    public void check(Long todoId, Long memberId){
        Optional<Todo> byId = todoRepository.findById(todoId);
        Todo todo = byId
                .orElseThrow(() -> new RuntimeException("TODOS NOT FOUND"));
        if(!todo.getMember().getId().equals(memberId))
            throw new RuntimeException("MEMBER NOT FOUND");
        todo.changeIsDone();
    }
}
