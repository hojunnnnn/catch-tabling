package com.catchtabling.common.domain;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.ValidException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor
public class Code {
    private static final int MAX_CODE_LENGTH = 10;

    @Size(max = MAX_CODE_LENGTH)
    @Column(name = "member_inner_id")
    private String code;

    public Code(String code) {
        validateLength(code);
        this.code = code;
    }

    public static Code generateCode(CodeGenerator generator) {
        return new Code(generator.generate(MAX_CODE_LENGTH));
    }

    private void validateLength(String code) {
        if (code.length() != MAX_CODE_LENGTH) {
            throw new ValidException(ErrorCode.INVALID_REQUEST, "code의 길이는 10자여야 합니다.");
        }
    }

}
