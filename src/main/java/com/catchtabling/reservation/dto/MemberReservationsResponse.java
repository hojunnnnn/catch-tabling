package com.catchtabling.reservation.dto;

import com.catchtabling.common.util.LocalDateTimeUtil;
import com.catchtabling.reservation.domain.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public record MemberReservationsResponse(
        List<MemberReservationResponse> reservations
) {

    public static MemberReservationsResponse from(List<Reservation> memberReservations) {
        List<MemberReservationResponse> list = memberReservations.stream()
                .map(reservation -> new MemberReservationResponse(
                reservation.getReservationNumber(),
                LocalDateTimeUtil.localDateTimeToString(reservation.getVisitDateTime()),
                reservation.getVisitorCount(),
                reservation.getRequestMemo(),
                reservation.getState(),
                MemberReservationStoreResponse.from(reservation.getRestaurant()))).collect(Collectors.toList());
        return new MemberReservationsResponse(list);
    }
}
