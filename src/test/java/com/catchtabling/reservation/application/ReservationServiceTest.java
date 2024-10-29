package com.catchtabling.reservation.application;

import com.catchtabling.common.domain.Code;
import com.catchtabling.common.domain.CodeGenerator;
import com.catchtabling.common.exception.customex.AlreadyReservedException;
import com.catchtabling.common.exception.customex.BadRequestException;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import com.catchtabling.store.domain.OpenStatus;
import com.catchtabling.store.domain.Store;
import com.catchtabling.store.domain.StoreDuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    LocalDateTime _7월_19일_13시 = LocalDateTime.of(2077,7,19,13,0,0);
    LocalDateTime _7월_19일_23시 = LocalDateTime.of(2077,7,19,23,0,0);
    Store store;

    @BeforeEach
    void setup() {
        store = Store.builder()
                .code(Code.generateCode(length -> "1000000001"))
                .name("광화문미진")
                .telNumber("023451100")
                .intro("대한민국 1등 메밀국수!")
                .address("서울 종로구 종로 19 1층")
                .status(OpenStatus.OPEN)
                .storeDuration(new StoreDuration(LocalTime.of(10,0),LocalTime.of(22,0)))
                .build();
        ReflectionTestUtils.setField(store, "id", 1L);
    }
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
                    _7월_19일_13시
            );
            //when & then
            assertThatThrownBy(() -> reservationService.validate(store, request))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("인원은 최소 1명 이상이어야 합니다.");
        }

        @Test
        void 이미_예약이_존재하는_경우_예외가_발생한다() {
            //given
            ReservationV1Request request = new ReservationV1Request(
                    1L,
                    1L,
                    2,
                    "창가 자리로 부탁드립니다.",
                    _7월_19일_13시
            );
            //when
            reservationService.reserve(request);
            //then
            assertThatThrownBy(() -> reservationService.validate(store,request))
                    .isInstanceOf(AlreadyReservedException.class)
                    .hasMessage("예약이 이미 존재합니다.");

        }

        @Test
        void 영업_외_시간에_예약하면_예외가_발생한다() {
            //given
            ReservationV1Request request = new ReservationV1Request(
                    1L,
                    1L,
                    2,
                    "창가 자리로 부탁드립니다.",
                    _7월_19일_23시
            );
            //when & then
            assertThatThrownBy(() -> reservationService.validate(store,request))
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
                    _7월_19일_13시
            );

            //when
            ReservationV1Response response = reservationService.reserve(request);

            //then
            assertThat(response).isNotNull();

        }
    }


}