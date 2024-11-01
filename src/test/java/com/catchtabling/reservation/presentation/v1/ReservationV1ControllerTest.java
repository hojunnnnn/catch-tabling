package com.catchtabling.reservation.presentation.v1;

import com.catchtabling.reservation.application.ReservationService;
import com.catchtabling.reservation.domain.EntryState;
import com.catchtabling.reservation.dto.MemberReservationResponse;
import com.catchtabling.reservation.dto.MemberReservationStoreResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationV1Controller.class)
@SuppressWarnings("NonAsciiCharacters")
class ReservationV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    class 예약_상세_조회 {
        final String url = "/api/v1/reservations/{reservationNum}";

        @Nested
        @DisplayName("GET " + url)
        class 올바른_주소로 {

            @Test
            void 요청을_보내면_200_응답과_예약_상세_정보가_반환된다() throws Exception {
                // given
                var expect = new MemberReservationResponse(
                        "1234567890",
                        "2024-10-31 13:00:00",
                        2,
                        "창가 자리로 부탁드려요.",
                        EntryState.PENDING,
                        new MemberReservationStoreResponse(
                                "치즈룸X테이스팅룸",
                                "023451100",
                                "서울특별시 송파구 올림픽로 300 롯데월드몰 5F"
                        )
                );
                given(reservationService.getDetails(anyString()))
                        .willReturn(expect);

                // when & then
                mockMvc.perform(get(url, "1234567890")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.status").value(200))
                        .andExpect(jsonPath("$.error").isEmpty())
                        .andExpect(jsonPath("$.message.reservationNumber").value("1234567890"))
                        .andExpect(jsonPath("$.message.visitDateTime").value("2024-10-31 13:00:00"))
                        .andExpect(jsonPath("$.message.store.name").value("치즈룸X테이스팅룸"))
                        .andExpect(jsonPath("$.path").value("/api/v1/reservations/1234567890"));
            }

            @Test
            void 조회하려는_예약이_없으면_404_응답이_반환된다() throws Exception {
                mockMvc.perform(get(url, "")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }

}