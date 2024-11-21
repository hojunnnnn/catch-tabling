package com.catchtabling.reservation.domain;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.UnexpectedException;
import com.catchtabling.common.exception.customex.ValidException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("NonAsciiCharacters")
class EntryStateTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    void 유효하지않은_인덱스로_생성시_예외(int index) {
        // when & then
        assertThatThrownBy(() -> EntryState.from(index))
                .isInstanceOf(UnexpectedException.class)
                .hasMessageStartingWith("EntryState의 인덱스가 올바르지 않습니다. ");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void 인덱스로_생성_성공(int index) {
        // when & then
        assertThatNoException().isThrownBy(() -> EntryState.from(index));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "ABCDEFG"})
    void 유효하지않은_값으로_요청시_예외(String value) {
        // when & then
        assertThatThrownBy(() -> EntryState.fromJson(value))
                .isInstanceOf(ValidException.class)
                .hasMessage(ErrorCode.INVALID_VALUE.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {"PENDING", "CONFIRMED", "VISITED", "CANCELLED", "NO_SHOW"})
    void 올바른_값으로_요청시_성공(String value) {
        // when & then
        EntryState entryState = EntryState.fromJson(value);
        assertThat(entryState).isEqualTo(EntryState.valueOf(value));
    }

}