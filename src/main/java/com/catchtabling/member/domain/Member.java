package com.catchtabling.member.domain;

import com.catchtabling.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter @NoArgsConstructor
@Table(name = "MEMBER_INFO")
@Entity
public class Member extends BaseTimeEntity {

    private static final int MEMBER_INNER_ID_MAX_LENGTH = 30;
    private static final int EMAIL_MAX_LENGTH = 50;
    private static final int NICKNAME_MAX_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = MEMBER_INNER_ID_MAX_LENGTH)
    @NotNull(message = "회원 내부 아이디는 null 일 수 없습니다.")
    @Column(name = "member_inner_id")
    private String memberInnerId;

    @Size(max = EMAIL_MAX_LENGTH)
    @NotNull(message = "이메일은 null 일 수 없습니다.")
    @Email
    @Column(name ="email")
    private String email;

    @Size(max = NICKNAME_MAX_LENGTH)
    @NotNull(message = "닉네임은 null 일 수 없습니다.")
    @Column(name ="nickname")
    private String nickname;

    // 비밀번호 사이즈 검사해야됨. 여기서는 암호화된 문자열이라 validation 불가
    @NotNull(message = "비밀번호는 null 일 수 없습니다.")
    @Column(name ="password")
    private String password;

    @NotNull(message = "전화번호는 null 일 수 없습니다.")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder
    public Member(String memberInnerId, String email, String nickname, String password, String phoneNumber) {
        this.memberInnerId = memberInnerId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


}
