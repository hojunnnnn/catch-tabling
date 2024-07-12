package com.catchtabling.member.application;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.NotFoundException;
import com.catchtabling.member.domain.Member;
import com.catchtabling.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
