package com.catchtabling.reservation.application;

import com.catchtabling.reservation.domain.EntryState;
import com.catchtabling.reservation.domain.Reservation;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
class ReservationStateServiceTest {

    LocalDateTime 다음날_13시 = LocalDateTime.of(
            LocalDate.now().plusDays(1),
            LocalTime.of(13,0,0)
    );

    @Autowired
    private ReservationStateService reservationStateService;

    @Autowired
    private ReservationService reservationService;



    @Nested
    class 예약_상태_변경 {

        @ParameterizedTest
        @EnumSource(
                value = EntryState.class,
                names = "VISITED"
        )
        void 성공(EntryState state) {
            // given
            String reservationNum = createReservation().reservationNumber();

            // when
            reservationStateService.update(reservationNum, state);
            Reservation reservation = reservationService.findByReservationNum(reservationNum);

            // then
            assertThat(reservation.getState()).isEqualTo(state);

        }

        private ReservationV1Response createReservation() {
            ReservationV1Request request = new ReservationV1Request(
                    1L,
                    1L,
                    2,
                    "창가 자리로 부탁드립니다.",
                    다음날_13시
            );
            return reservationService.reserve(request);
        }
    }
}