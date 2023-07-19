package com.example.demo.config.domain.dto;

import com.example.demo.members.domain.entity.Member;
import lombok.Getter;

@Getter
public class MemberDto {
    private Long id;
    private String email;
    private String name;
    private Integer age;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.age = member.getAge();
    }
}
