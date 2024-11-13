package com.catchtabling.reservation.repository;

import com.catchtabling.reservation.domain.ReservationSchedule;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;

public interface ReservationScheduleRepository extends JpaRepository<ReservationSchedule, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ReservationSchedule findByRestaurantIdAndReservationDateTime(Long storeId, LocalDateTime reservationDateTime);

}
