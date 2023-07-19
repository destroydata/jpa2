package com.example.demo.todos.service;

import com.example.demo.config.service.MemberLoginService;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberLoginService memberLoginService;
    public void insert(TodoRequest request){
        memberLoginService.findByMember(request.getMemberId());
        todoRepository.save(request.toEntity());
    }
}
