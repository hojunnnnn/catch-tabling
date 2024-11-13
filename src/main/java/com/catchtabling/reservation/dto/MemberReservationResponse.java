package com.catchtabling.reservation.dto;

import com.catchtabling.common.util.LocalDateTimeUtil;
import com.catchtabling.reservation.domain.Reservation;
import com.catchtabling.reservation.domain.EntryState;

public record MemberReservationResponse(
        String reservationNumber,
        String visitDateTime,
        Integer visitorCount,
        String requestMemo,
        EntryState status,
        MemberReservationStoreResponse store
) {

    public static MemberReservationResponse from(Reservation reservation) {
        return new MemberReservationResponse(
                reservation.getReservationNumber(),
                LocalDateTimeUtil.localDateTimeToString(reservation.getVisitDateTime()),
                reservation.getVisitorCount(),
                reservation.getRequestMemo(),
                reservation.getState(),
                MemberReservationStoreResponse.from(reservation.getRestaurant())
        );
    }

}
