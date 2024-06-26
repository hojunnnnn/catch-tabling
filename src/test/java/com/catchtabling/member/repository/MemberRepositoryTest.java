package com.catchtabling.member.repository;

import com.catchtabling.member.domain.Member;
import com.catchtabling.member.infrastructure.MemberRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(showSql = false)
@SuppressWarnings("NonAsciiCharacters")
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 멤버_생성() {
        // given
        Member member = Member.builder()
                .memberInnerId("memberInnerId")
                .email("email@email.com")
                .nickname("nickname")
                .password("password")
                .phoneNumber("phoneNumber")
                .build();
        // when
        Member savedMember = memberRepository.save(member);
        // then
        Member findMember = memberRepository.findById(savedMember.getId()).orElse(null);
        assertThat(findMember).isNotNull();
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
    }

    @Nested
    class Validator {

        @Test
        void memberInnerId_null_이면_예외() {
            // given
            Member member = Member.builder()
                    .memberInnerId(null)
                    .email("email@email.com")
                    .nickname("nickname")
                    .password("password")
                    .phoneNumber("phoneNumber")
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void memberInnerId_30자를_초과하면_예외() {
            // given
            String memberInnerId = "1".repeat(31);
            Member member = Member.builder()
                    .memberInnerId(memberInnerId)
                    .email("email@email.com")
                    .nickname("nickname")
                    .password("password")
                    .phoneNumber("phoneNumber")
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void email_null_이면_예외() {
            // given
            Member member = Member.builder()
                    .memberInnerId("memberInnerId")
                    .email(null)
                    .nickname("nickname")
                    .password("password")
                    .phoneNumber("phoneNumber")
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void email_형식이_맞지_않으면_예외() {
            // given
            String email = "email";
            Member member = Member.builder()
                    .memberInnerId("memberInnerId")
                    .email(email)
                    .nickname("nickname")
                    .password("password")
                    .phoneNumber("phoneNumber")
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void email_255자를_초과하면_예외() {
            // given
            String email = "1".repeat(256) + "@email.com";
            Member member = Member.builder()
                    .memberInnerId("memberInnerId")
                    .email(email)
                    .nickname("nickname")
                    .password("password")
                    .phoneNumber("phoneNumber")
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void nickname_null_이면_예외() {
            // given
            Member member = Member.builder()
                    .memberInnerId("memberInnerId")
                    .email("email@email.com")
                    .nickname(null)
                    .password("password")
                    .phoneNumber("phoneNumber")
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void nickname_255자를_초과하면_예외() {
            // given
            String nickname = "1".repeat(256);
            Member member = Member.builder()
                    .memberInnerId("memberInnerId")
                    .email("email@email.com")
                    .nickname(nickname)
                    .password("password")
                    .phoneNumber("phoneNumber")
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void password_null_이면_예외() {
            // given
            Member member = Member.builder()
                    .memberInnerId("memberInnerId")
                    .email("email@email.com")
                    .nickname("nickname")
                    .password(null)
                    .phoneNumber("phoneNumber")
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void phoneNumber_null_이면_예외() {
            // given
            Member member = Member.builder()
                    .memberInnerId("memberInnerId")
                    .email("email@email.com")
                    .nickname("nickname")
                    .password("password")
                    .phoneNumber(null)
                    .build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }
    }
}
