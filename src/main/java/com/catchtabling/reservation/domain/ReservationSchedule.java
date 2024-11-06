package com.catchtabling.reservation.domain;

import com.catchtabling.common.exception.customex.ErrorCode;
import com.catchtabling.common.exception.customex.ReserveFailException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "reservation_schedule",
        uniqueConstraints = @UniqueConstraint(columnNames = {"store_info_id", "reservation_date_time"})
)
@Entity
public class ReservationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_info_id")
    private Long storeId;

    @NotNull(message = "예약 일시는 Null 일 수 없습니다.")
    @Column(name = "reservation_date_time")
    private LocalDateTime reservationDateTime;

    @NotNull(message = "남은 자리는 Null 일 수 없습니다.")
    private Integer remainCount;

    @NotNull(message = "예약 가능 여부는 Null 일 수 없습니다.")
    private Boolean isReservable;


    public ReservationSchedule(Long storeId, LocalDateTime reservationDateTime, Integer capacity) {
        this.storeId = storeId;
        this.reservationDateTime = reservationDateTime;
        this.remainCount = capacity;
        this.isReservable = isOverRemainCount();
    }


    public ReservationSchedule reserve(Integer visitorCount) {
        if (!this.isReservable || visitorCount > this.remainCount) {
            throw new ReserveFailException(ErrorCode.FULL_RESERVATION);
        }
        this.remainCount -= visitorCount;
        this.isReservable = isOverRemainCount();
        return this;
    }

    private boolean isOverRemainCount() {
        return this.isReservable = this.remainCount > 0;
    }

}
