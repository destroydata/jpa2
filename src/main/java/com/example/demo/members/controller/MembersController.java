package com.example.demo.members.controller;


import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MembersController {
    private final MemberService service;
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest){
        return service.login(loginRequest);
    }

}
