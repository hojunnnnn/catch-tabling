package com.catchtabling.reservation.dto;

import com.catchtabling.reservation.domain.EntryState;
import jakarta.validation.constraints.NotNull;

public record ReservationStateUpdateRequest(
        @NotNull
        EntryState entryState
) {
}
