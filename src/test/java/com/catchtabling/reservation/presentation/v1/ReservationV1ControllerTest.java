package com.catchtabling.reservation.presentation.v1;

import com.catchtabling.reservation.application.ReservationService;
import com.catchtabling.reservation.domain.EntryState;
import com.catchtabling.reservation.dto.MemberReservationResponse;
import com.catchtabling.reservation.dto.MemberReservationStoreResponse;
import com.catchtabling.reservation.dto.MemberReservationsResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
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
            void 조회하려는_예약이_없으면_404_응답이_반환된다() throws Exception {
                mockMvc.perform(get(url, "")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }

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
        }
    }

    @Nested
    class 유저_예약_목록_조회 {
        final String url = "/api/v1/reservations";

        @Nested
        @DisplayName("GET " + url)
        class 올바른_주소로 {

            @Test
            void 쿼리_파라미터에_memberId를_보내지_않으면_400_응답이_반환된다() throws Exception {
                mockMvc.perform(get(url))
                        .andExpect(status().isBadRequest());
            }

            @Test
            void 쿼리_파라미터에_state와_page와_size를_보내지_않아도_200_응답이_반환된다() throws Exception {
                mockMvc.perform(get(url)
                                .param("memberId","1"))
                        .andExpect(status().isOk());
            }

            @Test
            void 요청을_보내면_200_응답과_유저의_예약_목록_정보가_반환된다() throws Exception {
                // given
                var expect = new MemberReservationsResponse(
                        List.of(
                                new MemberReservationResponse(
                                        "1111111111",
                                        "2024-11-05 13:00:00",
                                        2,
                                        "창가 자리로 부탁드려요.",
                                        EntryState.PENDING,
                                        new MemberReservationStoreResponse(
                                                "치즈룸X테이스팅룸",
                                                "023451100",
                                                "서울특별시 송파구 올림픽로 300 롯데월드몰 5F"
                                        )
                                ),
                                new MemberReservationResponse(
                                        "2222222222",
                                        "2024-11-11 13:00:00",
                                        2,
                                        "창가 자리로 부탁드려요.",
                                        EntryState.PENDING,
                                        new MemberReservationStoreResponse(
                                                "푸슈",
                                                "023451110",
                                                "서울특별시 서초구 서초대로19길 10-11 1층"
                                        )
                                )
                        )
                );
                given(reservationService.getReservationList(
                        anyLong(),
                        any(EntryState.class),
                        any(Pageable.class)))
                        .willReturn(expect);

                // when & then
                String content = mockMvc.perform(get(url)
                                .param("memberId", "1")
                                .param("state", "0")
                                .param("page", "0")
                                .param("size", "5"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(StandardCharsets.UTF_8);

                JsonNode rootNode = objectMapper.readTree(content);
                String extract = rootNode.path("message").toString();
                var actual = objectMapper.readValue(extract, MemberReservationsResponse.class);
                assertThat(actual).isEqualTo(expect);
            }
        }
    }

}