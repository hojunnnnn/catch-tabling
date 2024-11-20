package com.catchtabling.reservation.application;

import com.catchtabling.common.exception.customex.UnexpectedException;
import com.catchtabling.reservation.domain.EntryState;
import com.catchtabling.reservation.domain.Reservation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationStateService {

    private final ReservationService reservationService;

    @Transactional
    public void update(String reservationNum, EntryState state) {
        switch (state) {
            case PENDING -> pending(reservationNum);
            case CONFIRMED -> confirm(reservationNum);
            case VISITED -> visit(reservationNum);
            case CANCELLED -> cancel(reservationNum);
            case NO_SHOW -> noShow(reservationNum);
            default -> throw new UnexpectedException("EntryState의 index가 올바르지 않습니다. : " + state);
        }
    }

    private void noShow(String reservationNum) {
        Reservation reservation = reservationService.findByReservationNum(reservationNum);
        reservation.noShow();
        log.info("reservation {}'s state update to {}", reservationNum, reservation.getState());
    }

    private void cancel(String reservationNum) {
        Reservation reservation = reservationService.findByReservationNum(reservationNum);
        reservation.cancel();
        log.info("reservation {}'s state update to {}", reservationNum, reservation.getState());
    }

    private void visit(String reservationNum) {
        Reservation reservation = reservationService.findByReservationNum(reservationNum);
        reservation.visit();
        log.info("reservation {}'s state update to {}", reservationNum, reservation.getState());
    }

    private void confirm(String reservationNum) {
        Reservation reservation = reservationService.findByReservationNum(reservationNum);
        reservation.confirm();
        log.info("reservation {}'s state update to {}", reservationNum, reservation.getState());
    }

    private void pending(String reservationNum) {
        Reservation reservation = reservationService.findByReservationNum(reservationNum);
        reservation.pending();
        log.info("reservation {}'s state update to {}", reservationNum, reservation.getState());

    }
}
