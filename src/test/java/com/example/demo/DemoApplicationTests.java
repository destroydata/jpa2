package com.example.demo;

import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

}
