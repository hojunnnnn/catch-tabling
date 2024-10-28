package com.catchtabling.reservation.concurrency;

import com.catchtabling.reservation.application.ReservationService;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.repository.ReservationRepository;
import com.catchtabling.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private StoreRepository storeRepository;

    private CountDownLatch countDownLatch;
    private ExecutorService executorService;

    @BeforeEach
    public void setup() {
        this.countDownLatch = new CountDownLatch(threadCount);
        this.executorService = Executors.newFixedThreadPool(threadCount);
    }

    @Test
    public void 동시에_100명이_예약해도_1명만_성공한다() throws InterruptedException {
        ReservationV1Request request = new ReservationV1Request(
                1L,
                1L,
                2,
                "창가 자리로 부탁드립니다.",
                다음날_13시
        );

        for(int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    reservationService.reserve(request);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        long reserveCount = reservationRepository.countByStoreAndVisitDateTime(
                storeRepository.getReferenceById(1L),
                다음날_13시);
        assertThat(reserveCount).isEqualTo(1);
    }

}