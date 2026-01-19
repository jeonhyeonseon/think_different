package com.think_different.think_different.member.repository;

import com.think_different.think_different.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
}
