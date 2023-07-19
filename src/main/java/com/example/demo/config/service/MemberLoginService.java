package com.example.demo.config.service;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
    private final MemberLoginRepository memberLoginRepository;
//     TODO: INSERT
    public void insert(Member member){
        MemberLogin memberLogin = new MemberLogin(member, LocalDateTime.now());
        memberLoginRepository.save(memberLogin);
    }
//    TODO: login check 하는 거를 만들거 findByMember
    public Member findByMember(Long memberId){
        // 1. test member가 빠져나오냐(memberId 랑 같은)
        // 2. 가장 최근것이 빠져나오냐?
// 3. 없으면 에러발생
        Optional<MemberLogin> byMemberId =
                memberLoginRepository.findFirstByMemberIdOrderByEndAtDesc(memberId);
        return byMemberId
                .orElseThrow(()-> new RuntimeException("로그인 상태가 아닙니다."))
                .getMember();
    }

}
