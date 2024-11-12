package com.catchtabling.store.domain;

import com.catchtabling.common.exception.customex.BadRequestException;
import com.catchtabling.common.exception.customex.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDuration {

    @NotNull(message = "오픈 시간은 Null 일 수 없습니다.")
    @Column(name = "open_time")
    private LocalTime openTime;

    @NotNull(message = "닫는 시간은 Null 일 수 없습니다.")
    @Column(name = "close_time")
    private LocalTime closeTime;

    public StoreDuration(LocalTime openTime, LocalTime closeTime) {
        validate(openTime, closeTime);
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    private void validate(LocalTime openTime, LocalTime closeTime) {
        if(openTime.isAfter(closeTime)) {
            throw new BadRequestException(ErrorCode.INVALID_STORE_DURATION);
        }
    }

    public boolean isNotInDuration(LocalTime time) {
        return time.isBefore(openTime) || time.isAfter(closeTime);
    }

}
