package com.example.demo.members.repository;

import com.example.demo.members.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    select * from members where email = ? and password = ?;
    Optional<Member> findByEmailAndPassword(String email, String password);
    Optional<Member> findByEmail(String email);
    @Query("select m from Member m left join fetch m.todos t order by m.age desc")
    Page<Member> findAllFetch(Pageable request);
}
