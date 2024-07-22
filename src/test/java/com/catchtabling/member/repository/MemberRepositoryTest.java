package com.catchtabling.member.repository;

import com.catchtabling.common.domain.Code;
import com.catchtabling.member.domain.Member;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
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

    Member.MemberBuilder memberBuilder;

    @BeforeEach
    void setUp() {
        this.memberBuilder = Member.builder()
                .memberInnerId(new Code("innerId"))
                .email("email@email.com")
                .nickname("nickname")
                .password("password")
                .phoneNumber("phoneNumber");
    }

    @Test
    void 멤버_생성() {
        // given
        Member member = memberBuilder.build();
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
            Member member = memberBuilder.memberInnerId(null).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void memberInnerId_10자를_초과하면_예외() {
            // given
            String memberInnerId = "1".repeat(11);
            Member member = memberBuilder.memberInnerId(new Code(memberInnerId)).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void email_null_이면_예외() {
            // given
            Member member = memberBuilder.email(null).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void email_형식이_맞지_않으면_예외() {
            // given
            String email = "email";
            Member member = memberBuilder.email(email).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void email_50자를_초과하면_예외() {
            // given
            String email = "1".repeat(51) + "@email.com";
            Member member = memberBuilder.email(email).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void nickname_null_이면_예외() {
            // given
            Member member = memberBuilder.nickname(null).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void nickname_50자를_초과하면_예외() {
            // given
            String nickname = "1".repeat(51);
            Member member = memberBuilder.nickname(nickname).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void password_null_이면_예외() {
            // given
            Member member = memberBuilder.password(null).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void phoneNumber_null_이면_예외() {
            // given
            Member member = memberBuilder.phoneNumber(null).build();
            // when & then
            assertThatThrownBy(() -> memberRepository.save(member))
                    .isInstanceOf(ConstraintViolationException.class);
        }
    }
}
