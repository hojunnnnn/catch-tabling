package com.catchtabling.restaurant.domain;

import com.catchtabling.common.exception.customex.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
public class RestaurantDurationTest {

    LocalTime _10시 = LocalTime.of(10,0);
    LocalTime _13시 = LocalTime.of(13,0);
    LocalTime _22시 = LocalTime.of(22,0);

    @Test
    void 오픈_시간이_닫는_시간_이후면_예외() {
        // when & then
        assertThatThrownBy(() -> new RestaurantDuration(_22시, _13시))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("오픈시간은 닫는시간보다 이전이어야 합니다.");
    }

    @Nested
    class isNotInDuration {

        @Test
        void 기간에_포함되면_거짓() {
            // given
            RestaurantDuration restaurantDuration = new RestaurantDuration(_10시, _22시);

            // when
            boolean actual = restaurantDuration.isNotInDuration(_13시);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        void 기간에_포함되지_않으면_참() {
            // given
            RestaurantDuration restaurantDuration = new RestaurantDuration(_10시, _13시);

            // when
            boolean actual = restaurantDuration.isNotInDuration(_22시);

            // then
            assertThat(actual).isTrue();

        }
    }

}