package com.catchtabling.reservation.dto;

import com.catchtabling.common.util.LocalDateTimeUtil;
import com.catchtabling.reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

public record MemberReservationsResponse(
        List<MemberReservationResponse> list
) {

    public static MemberReservationsResponse from(List<Reservation> memberReservations) {
        List<MemberReservationResponse> list = new ArrayList<>();
        for(Reservation reservation : memberReservations) {
            list.add(new MemberReservationResponse(
                    reservation.getReservationNumber(),
                    LocalDateTimeUtil.localDateTimeToString(reservation.getVisitDateTime()),
                    reservation.getVisitorCount(),
                    reservation.getRequestMemo(),
                    reservation.getState(),
                    MemberReservationStoreResponse.from(reservation.getStore()))
            );
        }
        return new MemberReservationsResponse(list);
    }
}
