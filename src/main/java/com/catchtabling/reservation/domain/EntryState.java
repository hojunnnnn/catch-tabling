package com.catchtabling.reservation.domain;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.UnexpectedException;
import com.catchtabling.common.exception.customex.ValidException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;


@Getter
public enum EntryState {
    PENDING(0),
    CONFIRMED(1),
    VISITED(2),
    CANCELLED(3),
    NO_SHOW(4),

    ;

    private final int index;

    EntryState(int index) { this.index = index; }

    public static EntryState from(int index) {
        return switch (index) {
            case 0 -> PENDING;
            case 1 -> CONFIRMED;
            case 2 -> VISITED;
            case 3 -> CANCELLED;
            case 4 -> NO_SHOW;
            default -> throw new UnexpectedException("EntryState의 인덱스가 올바르지 않습니다. : " + index);
        };
    }

    /**
     * Spring Controller에서 DTO에 Enum 타입 필드를 사용할 때, 클라이언트가 유효하지 않은 Enum 값을
     * 포함한 JSON 요청을 보내면 Jackson의 JSON 파싱 과정에서 `HttpMessageNotReadableException`이
     * 발생하여 요청이 즉시 실패하게 되어 원하는 방식으로 handling이 불가능했다.
     * <p>
     * `@JsonCreator`로 JSON 값을 Java Enum으로 매핑하는 방식을 직접 정의하여,
     * JSON 파싱 단계에서 예외를 발생시키지 않고, 유효한 Enum 값으로 매핑하거나
     * 유효하지 않은 값에 대해 커스텀 예외를 발생시켜 일관된 형식으로 handling 할 수 있다.
     *
     * @param value 요청에서 전달된 Enum 값(String 형태)
     * @return 매칭되는 Enum 값, 없으면 null
     * @throws ValidException 유효하지 않은 값이 전달될 경우
     */
    @JsonCreator
    public static EntryState fromJson(String value) {
        return Arrays.stream(values())
                .filter(type -> type.name().equals(value))
                .findAny()
                .orElseThrow(() -> new ValidException(ErrorCode.INVALID_VALUE));
    }
}