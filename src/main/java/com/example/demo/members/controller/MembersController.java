package com.example.demo.members.controller;


import com.example.demo.config.aspect.TokenRequired;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.request.SignupRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.domain.response.MemberResponse;
import com.example.demo.members.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MembersController {
    private final MemberService service;
    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(description = "성공", responseCode = "200"),
            @ApiResponse(description = "실패", responseCode = "400")
    })
    @Operation(description = "로그인", summary = "login")
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest){
        return service.login(loginRequest);
    }
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(
            @RequestBody SignupRequest request){
        service.insert(request);
    }
// 몇살 이상 이하 성으로 찾기
    @GetMapping
    @TokenRequired
    public Map<String , Object> getAll(
            @RequestParam(required = false, defaultValue = "0", name = "page")
            Integer page,
            @RequestParam(required = false, defaultValue = "3", name = "size")
            Integer size
    ){
        Page<MemberResponse> all = service.findAll(PageRequest.of(page, size));
        long totalElements = all.getTotalElements();
        int totalPages = all.getTotalPages();
        List<MemberResponse> content = all.getContent();
        Map<String , Object> map = new HashMap<>();
        map.put("totalElements",totalElements);
        map.put("totalPages",totalPages);
        map.put("content",content);
        return map;
    }
    @GetMapping("test")
    @TokenRequired
    public Map<String, Object> test(
            @RequestHeader("Authorization") String token
    ){

        return service.getTokenToData(token.replace("Bearer ",""));
    }
}
