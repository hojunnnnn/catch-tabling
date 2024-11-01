package com.catchtabling.reservation.repository;

import com.catchtabling.common.domain.Code;
import com.catchtabling.reservation.domain.Reservation;
import com.catchtabling.reservation.domain.EntryState;
import com.catchtabling.store.domain.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByStoreAndVisitDateTime(Store store, LocalDateTime visitDateTime);

    boolean existsByReservationNumber(Code reservationNum);

    long countByStoreAndVisitDateTime(Store store, LocalDateTime visitDateTime);

    @Query("""
        SELECT r
        FROM Reservation r
        JOIN FETCH r.store
        WHERE r.reservationNumber = :reservationNum
        """)
    Optional<Reservation> findByReservationNumWithFetch(@Param("reservationNum") Code reservationNum);

    @EntityGraph(attributePaths = {"store", "member"})
    List<Reservation> findAllByMemberIdAndState(Long memberId,
                                                 EntryState state,
                                                 Pageable pageable);
}
