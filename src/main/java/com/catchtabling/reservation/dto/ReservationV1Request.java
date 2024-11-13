package com.catchtabling.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationV1Request(
        @NotNull
        Long restaurantId,

        @NotNull
        Long memberId,

        @NotNull
        Integer visitorCount,

        @NotNull
        String requestMemo,

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime visitDateTime

//        @Valid
//        @NotNull
//        ReservationCreateRequest reservationCreateRequest
) {
}
