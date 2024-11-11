package com.catchtabling.reservation.application;

import com.catchtabling.common.exception.customex.AlreadyReservedException;
import com.catchtabling.common.exception.customex.BadRequestException;
import com.catchtabling.common.exception.customex.ReserveFailException;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    LocalDateTime 다음날_13시 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(13,0,0));
    LocalDateTime 다음날_23시 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(23,0,0));

    @Nested
    class 예약_등록 {

        @ParameterizedTest
        @ValueSource(ints = {-1, 0})
        void 예약_인원이_자연수가_아닌_경우_예외가_발생한다(int visitorCount) {
            //given
            ReservationV1Request request = new ReservationV1Request(
                    1L,
                    1L,
                    visitorCount,
                    "창가 자리로 부탁드립니다.",
                    다음날_13시
            );
            //when & then
            assertThatThrownBy(() -> reservationService.reserve(request))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("인원은 최소 1명 이상이어야 합니다.");
        }


        @ParameterizedTest
        @ValueSource(ints = 51)
        void 예약_인원이_수용_인원보다_클_경우_예외가_발생한다(int visitorCount) {
            //given
            ReservationV1Request request = new ReservationV1Request(
                    1L,
                    1L,
                    visitorCount,
                    "창가 자리로 부탁드립니다.",
                    다음날_13시
            );
            //when & then
            assertThatThrownBy(() -> reservationService.reserve(request))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("예약 인원은 최대 수용 가능 인원보다 적어야 합니다.");

        }

        @ParameterizedTest
        @ValueSource(ints = 49)
        void 예약_인원이_가득_찬_경우_예외가_발생한다(int visitorCount) {
            //given
            ReservationV1Request request = new ReservationV1Request(
                    1L,
                    1L,
                    visitorCount,
                    "창가 자리로 부탁드립니다.",
                    다음날_13시
            );
            //when
            reservationService.reserve(request);

            //then
            assertThatThrownBy(() -> reservationService.reserve(request))
                    .isInstanceOf(ReserveFailException.class)
                    .hasMessage("예약이 가득 찼습니다.");

        }

        @Test
        void 영업_외_시간에_예약하면_예외가_발생한다() {
            //given
            ReservationV1Request request = new ReservationV1Request(
                    1L,
                    1L,
                    2,
                    "창가 자리로 부탁드립니다.",
                    다음날_23시
            );
            //when & then
            assertThatThrownBy(() -> reservationService.reserve(request))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("영업 시간 내 예약만 가능합니다.");

        }

        @Test
        void 성공() {
            //given
            ReservationV1Request request = new ReservationV1Request(
                    2L,
                    1L,
                    2,
                    "창가 자리로 부탁드립니다.",
                    다음날_13시
            );

            //when
            ReservationV1Response response = reservationService.reserve(request);

            //then
            assertThat(response).isNotNull();

        }
    }


}