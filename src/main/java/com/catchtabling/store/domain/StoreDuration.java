package com.catchtabling.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Embeddable
@NoArgsConstructor
public class StoreDuration {

    @NotNull(message = "오픈 시간은 Null 일 수 없습니다.")
    @Column(name = "open_time")
    private LocalTime openTime;

    @NotNull(message = "닫는 시간은 Null 일 수 없습니다.")
    @Column(name = "close_time")
    private LocalTime closeTime;

    public StoreDuration(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
