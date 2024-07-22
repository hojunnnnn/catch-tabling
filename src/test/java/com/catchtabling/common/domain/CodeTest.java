package com.catchtabling.common.domain;

import com.catchtabling.common.exception.customex.ValidException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CodeTest {

    @Nested
    class Validator {

        @Test
        void code의_길이가_10자_미만이면_예외() {
            //given
            String code = "1".repeat(9);

            //when & then
            assertThatThrownBy(() -> new Code(code))
                    .isInstanceOf(ValidException.class);
        }

        @Test
        void 코드의_길이가_10자_초과이면_예외() {
            //given
            String code = "1".repeat(11);

            //when & then
            assertThatThrownBy(() -> new Code(code))
                    .isInstanceOf(ValidException.class);
        }


        @Test
        void 성공() {
            //given
            String code = "1".repeat(10);

            //when
            Code createdCode = new Code(code);

            //then
            assertThat(createdCode.getCode()).isEqualTo(code);
        }
    }
}
