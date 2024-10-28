package com.catchtabling.reservation.repository;

import com.catchtabling.common.domain.Code;
import com.catchtabling.reservation.domain.Reservation;
import com.catchtabling.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByStoreAndVisitDateTime(Store store, LocalDateTime visitDateTime);

    boolean existsByReservationNumber(Code reservationNum);

    long countByStoreAndVisitDateTime(Store store, LocalDateTime visitDateTime);
}
