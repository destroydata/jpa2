package com.example.demo.members.domain.response;

import com.example.demo.members.domain.entity.Member;

public record LoginResponse(
        Long id, String name, Integer age, String token ) {
}
