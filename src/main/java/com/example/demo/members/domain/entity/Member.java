package com.example.demo.members.domain.entity;


import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.todos.domain.entity.Todo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "members")
@Getter @AllArgsConstructor
@NoArgsConstructor @Builder
public class Member{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password, name;
    private Integer age;

    @OneToMany(mappedBy = "member")
    private List<Todo> todos;

    @OneToMany(mappedBy = "member")
    private List<MemberLogin> logins;
}
