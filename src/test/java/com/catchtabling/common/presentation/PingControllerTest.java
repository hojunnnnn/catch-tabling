package com.catchtabling.common.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
class PingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Nested
    class 핑_요청 {

        final String uri = "/ping";

        @Nested
        @DisplayName("GET " + uri)
        class 올바른_주소로 {

            @Test
            void 요청을_보내면_200_응답이_반환된다() throws Exception {
                mockMvc.perform(get(uri))
                        .andExpect(status().isOk());
            }
        }
    }
}
