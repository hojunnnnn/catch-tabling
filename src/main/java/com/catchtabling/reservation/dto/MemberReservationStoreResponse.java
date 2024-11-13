package com.catchtabling.reservation.dto;

import com.catchtabling.restaurant.domain.Restaurant;

public record MemberReservationStoreResponse(
        String name,
        String telNumber,
        String address
) {

    public static MemberReservationStoreResponse from(Restaurant restaurant) {
        return new MemberReservationStoreResponse(
                restaurant.getName(),
                restaurant.getTelNumber(),
                restaurant.getAddress()
        );
    }
}
