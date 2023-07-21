package com.example.demo.todos.domain.request;

import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class TodoRequest {
    private String title;
    private String content;
    private Long memberId;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Todo toEntity(){
        Member member = Member.builder()
                .id(memberId)
                .build();
//        new Todo(null, title, content, false, 0,member);
        return Todo.builder()
                .content(content)
                .title(title)
                .member(member)
                .likeCount(0)
                .build();
    }
}
