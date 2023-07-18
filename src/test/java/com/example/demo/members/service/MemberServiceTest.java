package com.example.demo.members.service;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.request.SignupRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.repository.MemberRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberLoginRepository memberLoginRepository;

    @BeforeEach
    void init(){
        String email = "1111";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);
        Member member = new Member(null, email, password, "name", 10, null, null);
        memberRepository.save(member);
    }

    @AfterEach
    void clear(){
        memberRepository.deleteAll();
        memberLoginRepository.deleteAll();
    }


    @Test
    void insert() {

        // given
        String email = "ssss";
        String password = "1234";
        String name = "uuuu";
        Integer age = 10;
        SignupRequest signupRequest =
                new SignupRequest(email, password, name, age);

        // when
        memberService.insert(signupRequest);

        // then
        List<Member> all = memberRepository.findAll();
        assertThat(all).hasSize(2);
        assertThat(all.get(1).getEmail()).isEqualTo(email);

    }

    @Test
    void 기본로그인() {
        // given
        String email = "1111";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);

        // when
        LoginResponse login = memberService.login(loginRequest);

        // then
        assertThat(login.age()).isEqualTo(10);
        assertThat(login.name()).isEqualTo("name");
        assertThat(login.id()).isNotNull();
    }

    @Test
    void 기본로그인_멤버로그인_인서트_체크() {

        // given
        String email = "1111";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);


        // when
        LoginResponse login = memberService.login(loginRequest);
        // find 2, login save 3

        // then
        assertThat(login.age()).isEqualTo(10);
        assertThat(login.name()).isEqualTo("name");
        assertThat(login.id()).isNotNull();
//      4
        List<MemberLogin> all = memberLoginRepository.findAll();
        assertThat(all).hasSize(1);
//        5
//        assertThat(all.get(0).getMember()).isEqualTo(member);
        assertThat(all.get(0).getCreateAt()).isBefore(LocalDateTime.now());
        assertThat(all.get(0).getEndAt()).isAfter(LocalDateTime.now());
    }

    @Test
    void 로그인시_없는_유저(){
        // given
        String email = "1111dd123";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);
        // when & then
        RuntimeException runtimeException = Assertions.assertThrows(
                RuntimeException.class
                , () -> memberService.login(loginRequest));
        assertThat(runtimeException).hasMessage("없는 유저");


//       assertThatThrownBy(()->memberService.login(loginRequest))
//               .hasMessage("없는 유저");
    }
}