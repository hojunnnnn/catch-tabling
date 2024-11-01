package com.catchtabling.common.exception.customex;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 400
    INVALID_REQUEST("잘못된 요청입니다."),
    ALREADY_RESERVED("예약이 이미 존재합니다."),
    INVALID_RESERVE_STORE_DURATION("영업 시간 내 예약만 가능합니다."),
    INVALID_VISITOR_MIN_SIZE("인원은 최소 1명 이상이어야 합니다."),
    VALIDATION_FAIL("검증에 실패하였습니다."),

    // 404
    STORE_NOT_FOUND("존재하지 않는 가게 입니다."),
    MEMBER_NOT_FOUND("존재하지 않는 멤버 입니다."),
    RESERVATION_NOT_FOUND("존재하지 않는 예약 입니다."),

    // 500
    INTERNAL_SERVER_ERROR("서버 내부에 문제가 발생했습니다.")


    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
