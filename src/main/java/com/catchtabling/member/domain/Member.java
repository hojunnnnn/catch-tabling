package com.catchtabling.member.domain;

import com.catchtabling.common.domain.BaseTimeEntity;
import com.catchtabling.common.domain.Code;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Table(name = "MEMBER_INFO")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final int MAX_NICKNAME_LENGTH = 50;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "회원 내부 아이디는 null 일 수 없습니다.")
    @AttributeOverride(name = "code", column = @Column(name = "member_inner_id"))
    @Embedded
    private Code memberInnerId;

    @Size(max = MAX_EMAIL_LENGTH)
    @NotNull(message = "이메일은 null 일 수 없습니다.")
    @Email
    private String email;

    @Size(max = MAX_NICKNAME_LENGTH)
    @NotNull(message = "닉네임은 null 일 수 없습니다.")
    private String nickname;

    // 비밀번호 사이즈 검사해야됨. 여기서는 암호화된 문자열이라 validation 불가
    @NotNull(message = "비밀번호는 null 일 수 없습니다.")
    private String password;

    @NotNull(message = "전화번호는 null 일 수 없습니다.")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder
    public Member(Code memberInnerId, String email, String nickname, String password, String phoneNumber) {
        this.memberInnerId = memberInnerId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getMemberInnerId() {
        return memberInnerId.getCode();
    }
}
