package com.example.demo.config.service;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberLoginServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberLoginRepository memberLoginRepository;
    @Autowired
    MemberLoginService memberLoginService;
    String email = "1111";
    String password = "1234";
    @BeforeEach
    void init(){
        Member member =
                new Member(null, email, password
                        , "name", 10, null, null);
        Member save = memberRepository.save(member);
        MemberLogin entity = new MemberLogin(member, LocalDateTime.now());
        memberLoginRepository.save(entity);
    }

    @AfterEach
    void clear(){
        memberRepository.deleteAll();
        memberLoginRepository.deleteAll();
    }

    @Test
    void findByMember() {
        //given
        Long memberId = 1l;

        //when
        Member byMember = memberLoginService.findByMember(memberId);

        //then
        Assertions.assertThat(byMember.getEmail()).isEqualTo(email);
        Assertions.assertThat(byMember.getPassword()).isEqualTo(password);
    }
    @Test
    void 가장최근것찾기() {
        //given
        Long memberId = 1l;
        Member member = new Member(memberId, null, null, null, null, null, null);
        MemberLogin entity = new MemberLogin(member, LocalDateTime.now());
        MemberLogin save = memberLoginRepository.save(entity);
        //when
        Member byMember = memberLoginService.findByMember(memberId);

        //then
        Assertions.assertThat(save.getMember()).isEqualTo(byMember);
    }


    @Test
    void insert() {
    }
}