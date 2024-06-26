package com.catchtabling.member.infrastructure;

import com.catchtabling.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
