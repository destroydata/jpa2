package com.example.demo.config.repository;

import com.example.demo.config.domain.entity.MemberLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLoginRepository
        extends JpaRepository<MemberLogin, Long> {
}
