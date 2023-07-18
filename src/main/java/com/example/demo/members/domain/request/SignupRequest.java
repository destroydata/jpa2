package com.example.demo.members.domain.request;


import com.example.demo.members.domain.entity.Member;

public record SignupRequest(String email
        , String password
        , String name
        , Integer age) {
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .age(age)
                .name(name)
                .build();
    }
}
