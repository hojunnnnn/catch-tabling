package com.catchtabling.reservation.repository;

import com.catchtabling.common.domain.Code;
import com.catchtabling.reservation.domain.Reservation;
import com.catchtabling.reservation.domain.EntryState;
import com.catchtabling.restaurant.domain.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByRestaurantAndVisitDateTime(Restaurant restaurant, LocalDateTime visitDateTime);

    boolean existsByReservationNumber(Code reservationNum);

    long countByRestaurantAndVisitDateTime(Restaurant restaurant, LocalDateTime visitDateTime);

    @Query("""
        SELECT r
        FROM Reservation r
        JOIN FETCH r.restaurant
        WHERE r.reservationNumber = :reservationNum
        """)
    Optional<Reservation> findByReservationNumWithFetch(@Param("reservationNum") Code reservationNum);

    @EntityGraph(attributePaths = {"restaurant", "member"})
    List<Reservation> findAllByMemberIdAndState(Long memberId,
                                                 EntryState state,
                                                 Pageable pageable);
}
