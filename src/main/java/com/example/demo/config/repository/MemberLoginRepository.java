package com.example.demo.config.repository;

import com.example.demo.config.domain.entity.MemberLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberLoginRepository
        extends JpaRepository<MemberLogin, Long> {
    Optional<MemberLogin> findFirstByMemberIdAndEndAtAfterOrderByEndAtDesc
            (Long memberId, LocalDateTime now);
}
