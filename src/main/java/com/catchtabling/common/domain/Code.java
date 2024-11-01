package com.catchtabling.common.domain;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.ValidException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {
    private static final int MAX_CODE_LENGTH = 10;

    @Size(max = MAX_CODE_LENGTH)
    @Column(name = "code")
    private String code;

    public Code(String code) {
        validateIsNotBlank(code);
        validateLength(code);
        this.code = code;
    }

    public static Code generateCode(CodeGenerator generator) {
        return new Code(generator.generate(MAX_CODE_LENGTH));
    }

    private void validateIsNotBlank(String code) {
        if (code.isBlank()) {
            throw new ValidException("공백을 제외한 문자를 입력해주세요.");
        }
    }
    private void validateLength(String code) {
        if (code.length() != MAX_CODE_LENGTH) {
            throw new ValidException("code의 길이는 10자여야 합니다.");
        }
    }

}
