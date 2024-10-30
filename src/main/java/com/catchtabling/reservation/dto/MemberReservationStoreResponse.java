package com.catchtabling.reservation.dto;

import com.catchtabling.store.domain.Store;

public record MemberReservationStoreResponse(
        String name,
        String telNumber,
        String address
) {

    public static MemberReservationStoreResponse from(Store store) {
        return new MemberReservationStoreResponse(
                store.getName(),
                store.getTelNumber(),
                store.getAddress()
        );
    }
}
