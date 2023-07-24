package com.example.demo;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.entity.QMember;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.repository.MemberRepository;
import com.example.demo.members.service.MemberService;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.repository.TodoRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberLoginRepository memberLoginRepository;
	@Autowired
	EntityManager em;


	@Test @Transactional
	void contextLoads() {
		memberLoginRepository.findFirstByMemberIdAndEndAtAfterOrderByEndAtDesc(1l, LocalDateTime.now());
	}
	@Test
	void test(){
		QMember member = new QMember("m");
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		JPAQuery<Member> from = queryFactory
				.select(member)
				.from(member);
		List<Member> fetch = from.fetch();
		System.out.println();

	}


	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TodoRepository todoRepository;
	@Autowired
	MemberLoginRepository memberLoginRepository;
	String email = "1111";
	String password = "1234";
	Member member;
	@Autowired
	EntityManager entityManager;
	@Autowired
	MemberService memberService;
	String token;
	@BeforeEach
	void init(){
		Member member =
				new Member(null, email, password
						, "name", 10, new ArrayList<>(), null);

		this.member = memberRepository.save(member);
		this.todo = todoRepository.save(
				new Todo(null, "a", "a"
						, false, 0, member)
		);
		for (int i = 0; i < 40; i++) {
			todoRepository.save(
					new Todo(null, "t" + i,"t" + i
							, false, i, member)
			);
		}

		MemberLogin entity = new MemberLogin(this.member, LocalDateTime.now());
		memberLoginRepository.save(entity);
		entityManager.flush();
		entityManager.clear();
		LoginResponse login = memberService.login(new LoginRequest(email, password));
		token = login.token();

	}
	@AfterEach
	void clean(){
		todoRepository.deleteAll();
		memberLoginRepository.deleteAll();
		memberRepository.deleteAll();
	}

}
