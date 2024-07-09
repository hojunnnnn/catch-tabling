package com.catchtabling.reservation.dto;

import com.catchtabling.common.util.LocalDateTimeUtil;
import com.catchtabling.reservation.domain.Reservation;

public record ReservationV1Response(
        String reservationNumber,
        Integer visitorCount,
        String requestMemo,
        String visitDateTime
) {

    public static ReservationV1Response from(Reservation reservation) {
        return new ReservationV1Response(
                reservation.getReservationNumber(),
                reservation.getVisitorCount(),
                reservation.getRequestMemo(),
                LocalDateTimeUtil.localDateTimeToString(reservation.getVisitDateTime())
        );
    }
}
