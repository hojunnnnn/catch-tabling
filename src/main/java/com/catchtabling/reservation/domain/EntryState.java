package com.catchtabling.reservation.domain;

import com.catchtabling.common.exception.customex.UnexpectedException;
import lombok.Getter;


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

}