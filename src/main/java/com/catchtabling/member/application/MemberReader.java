package com.catchtabling.member.application;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.NotFoundException;
import com.catchtabling.member.domain.Member;
import com.catchtabling.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
