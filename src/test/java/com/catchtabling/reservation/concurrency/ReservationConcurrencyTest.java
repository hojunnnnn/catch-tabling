package com.catchtabling.reservation.concurrency;

import com.catchtabling.reservation.application.ReservationService;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.repository.ReservationRepository;
import com.catchtabling.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
public class ReservationConcurrencyTest {
    private final int threadCount = 100;
    private final LocalDateTime 다음날_13시 = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(13, 0));

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private CountDownLatch countDownLatch;
    private ExecutorService executorService;

    @BeforeEach
    public void setup() {
        this.countDownLatch = new CountDownLatch(threadCount);
        this.executorService = Executors.newFixedThreadPool(threadCount);
    }

    @ParameterizedTest
    @ValueSource(ints = 2)
    void 최대_수용_인원이_50명일_때_동시에_100팀이_예약하면_25팀만_성공한다(int visitorCount) throws InterruptedException {
        for(int i = 1; i <= threadCount; i++) {
            ReservationV1Request request = new ReservationV1Request(
                    1L,
                    (long) i,
                    visitorCount,
                    "창가 자리로 부탁드립니다.",
                    다음날_13시
            );
            executorService.submit(() -> {
                try {
                    reservationService.reserve(request);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        long reserveCount = reservationRepository.countByRestaurantAndVisitDateTime(
                restaurantRepository.getReferenceById(1L),
                다음날_13시);
        assertThat(reserveCount).isEqualTo(25);
    }

}
