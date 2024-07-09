package com.catchtabling.reservation.application;

import com.catchtabling.member.application.MemberReader;
import com.catchtabling.member.domain.Member;
import com.catchtabling.reservation.domain.Reservation;
import com.catchtabling.reservation.dto.ReservationV1Request;
import com.catchtabling.reservation.dto.ReservationV1Response;
import com.catchtabling.reservation.repository.ReservationRepository;
import com.catchtabling.store.application.StoreReader;
import com.catchtabling.store.domain.Store;
import com.catchtabling.store.domain.StoreDuration;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final static int MIN_VISITOR_COUNT = 1;
    private final static int MAX_RESERVE_NO_LENGTH = 10;

    private final StoreReader storeReader;
    private final MemberReader memberReader;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationV1Response reserve(ReservationV1Request request) {
        Member member = memberReader.findMemberById(request.memberId());
        Store store = storeReader.findStoreById(request.storeId());

        validateVisitTime(
                store.getStoreDuration(),
                request.visitDateTime());
        validateAlreadyReserved(
                store,
                request.visitDateTime());
        validateVisitor(request.visitorCount());

        Reservation reservation = reservationRepository.save(Reservation.builder()
                .reservationNumber(createRandomNumeric())
                .requestMemo(request.requestMemo())
                .visitorCount(request.visitorCount())
                .visitDateTime(request.visitDateTime())
                .member(member)
                .store(store)
                .build());

        return ReservationV1Response.from(reservation);
    }

    private void validateVisitTime(StoreDuration duration, LocalDateTime visitDateTime) {
        if (duration.isNotInDuration(visitDateTime.toLocalTime())) {
            throw new IllegalArgumentException("영업 시간 내 예약만 가능합니다.");
        }
    }
    private void validateAlreadyReserved(Store store, LocalDateTime visitDateTime) {
        if (reservationRepository.existsByStoreAndVisitDateTime(store, visitDateTime)) {
            throw new IllegalStateException("이미 해당 시간에 예약이 존재합니다.");
        }

    }
    private void validateVisitor(Integer visitorCount) {
        if (visitorCount < MIN_VISITOR_COUNT) {
            throw new IllegalArgumentException("인원은 최소 1명 이상이어야 합니다.");
        }
    }

    private String createRandomNumeric() {
        String randomNumeric;
        do {
            randomNumeric = RandomStringUtils.randomNumeric(MAX_RESERVE_NO_LENGTH);
        } while (reservationRepository.existsByReservationNumber(randomNumeric));
        return randomNumeric;
    }
}
