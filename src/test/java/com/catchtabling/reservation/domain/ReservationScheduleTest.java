package com.catchtabling.reservation.domain;

import com.catchtabling.common.exception.customex.ReserveFailException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
class ReservationScheduleTest {

    private final LocalDateTime 다음날_13시 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(13, 0));
    private final int _10명 = 10;
    private final int _2명 = 2;

    @Nested
    class 스케줄_등록 {

        @Test
        void 수용_가능_인원만큼_예약하면_남은_자리가_0이_되고_예약_가능_여부가_false로_변경된다() {
            // given
            ReservationSchedule schedule = new ReservationSchedule(
                    1L,
                    다음날_13시,
                    _10명
            );

            // when
            schedule.reserve(_10명);

            // then
            assertThat(schedule.getRemainCount()).isEqualTo(0);
            assertThat(schedule.getIsReservable()).isFalse();
        }

        @Test
        void 예약_가능_여부가_false일_때_예약을_시도하면_예외가_발생한다() {
            // given
            ReservationSchedule schedule = new ReservationSchedule(
                    1L,
                    다음날_13시,
                    0
            );

            // when & then
            assertThatThrownBy(() -> schedule.reserve(_2명))
                    .isInstanceOf(ReserveFailException.class)
                    .hasMessage("예약이 가득 찼습니다.");
        }

        @ParameterizedTest
        @ValueSource(ints = 11)
        void 방문자_수가_수용_가능_수_보다_크면_예외가_발생한다(Integer visitorCount) {
            // given
            ReservationSchedule schedule = new ReservationSchedule(
                    1L,
                    다음날_13시,
                    _10명
            );

            // when & then
            assertThatThrownBy(() -> schedule.reserve(visitorCount))
                    .isInstanceOf(ReserveFailException.class)
                    .hasMessage("예약이 가득 찼습니다.");
        }

        @Test
        void 완료되면_남은_자릿수가_감소된다() {
            // given
            ReservationSchedule schedule = new ReservationSchedule(
                    1L,
                    다음날_13시,
                    _10명
            );

            // when
            schedule.reserve(_2명);

            // then
            int expected = _10명 - _2명;
            assertThat(schedule.getRemainCount()).isEqualTo(expected);
        }
    }

}